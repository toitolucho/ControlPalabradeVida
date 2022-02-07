/*
-------------------------------------------------------------------------------
Fingerprint SDK Sample
(c) 2005-2007 Griaule Tecnologia Ltda.
http://www.griaule.com
-------------------------------------------------------------------------------

This sample is provided with "Fingerprint SDK Recognition Library" and
can't run without it. It's provided just as an example of using Fingerprint SDK
Recognition Library and should not be used as basis for any
commercial product.

Griaule Biometrics makes no representations concerning either the merchantability
of this software or the suitability of this sample for any particular purpose.

THIS SAMPLE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL GRIAULE BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

You can download the trial version of Fingerprint SDK directly from Griaule website.

These notices must be retained in any copies of any part of this
documentation and/or sample.

-------------------------------------------------------------------------------
*/


package org.quarkbit.controlpalabravida.utils;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.ibatis.session.SqlSession;
import org.quarkbit.controlpalabravida.dao.Coneccion;
import org.quarkbit.controlpalabravida.dao.domain.PersonaHuellaDigital;
import org.quarkbit.controlpalabravida.dao.persistence.PersonaHuellaDigitalMapper;
import org.quarkbit.controlpalabravida.formularios.FDactilar;

import com.griaule.grfingerjava.FingerprintImage;
import com.griaule.grfingerjava.GrFingerJava;
import com.griaule.grfingerjava.GrFingerJavaException;
import com.griaule.grfingerjava.IFingerEventListener;
import com.griaule.grfingerjava.IImageEventListener;
import com.griaule.grfingerjava.IStatusEventListener;
import com.griaule.grfingerjava.MatchingContext;
import com.griaule.grfingerjava.Template;

import sun.misc.IOUtils;


/**
* Class responsible for handling Fingerprint SDK.
*
* It handles fingerprint loading/capturing, template extraction,
* fingerprint image saving and storing/retrieving from template base.
*/
public class Util implements IStatusEventListener, IImageEventListener, IFingerEventListener {

   /** Fingerprint SDK context used for capture / extraction / matching of fingerprints. */
   private MatchingContext fingerprintSDK;
   /** User interface, where logs, images and other things will be sent. */
   private FDactilar ui;

   /** Sets if template must be automatically extracted after capture. */
   private boolean autoExtract = true;
   /** Sets if template must be automatically identified after capture.
    It's only effective when *autoExtract == true) */
   private boolean autoIdentify = false;


   /** The last fingerprint image acquired. */
   private FingerprintImage fingerprint;
   /** The template extracted from the last acquired image. */
   private Template template;

   private SqlSession session;
   private PersonaHuellaDigitalMapper daoPersonaHuellaDigitalMapper;

   /**
    * Creates a new Util to be used by the specified Main Form.
    *
    * Initializes fingerprint capture and database connection.
    */
   public Util(FDactilar ui) {
       this.ui = ui;

       //Initializes DB connection
       initDB();
       //Initializes Fingerprint SDK and enables fingerprint capture.
       initFingerprintSDK();
       
       session = Coneccion.getSqlSessionFactory().openSession();
       daoPersonaHuellaDigitalMapper = session.getMapper(PersonaHuellaDigitalMapper.class);
       autoExtract = autoIdentify = true;
   }

   /**
    * Stops fingerprint capture and closes the database connection.
    */
   public void destroy() {
       destroyFingerprintSDK();
       destroyDB();
   }

   /**
    * Initializes Fingerprint SDK and enables fingerprint capture.
    */
   private void initFingerprintSDK() {
       try {
           fingerprintSDK = new MatchingContext();
           //Starts fingerprint capture.
           GrFingerJava.initializeCapture(this);

           ui.writeLog("**Fingerprint SDK Initialized Successfull**");

       } catch (Exception e) {
           //If any error ocurred while initializing GrFinger,
           //writes the error to log
           ui.writeLog(e.getMessage());
       }
   }

   /**
    * Stops fingerprint capture.
    */
   private void destroyFingerprintSDK() {
       try {
           GrFingerJava.finalizeCapture();
       } catch (GrFingerJavaException e) {
           e.printStackTrace();
       }
   }


   /**
    * This function is called every time a fingerprint reader is plugged.
    *
    * @see griaule.grFinger.StatusCallBack#onPlug(java.lang.String)
    */
   public void onSensorPlug(String idSensor) {
       //Logs the sensor has been pluged.
       ui.writeLog("Sensor: "+idSensor+". Event: Plugged.");
       try {
           //Start capturing from plugged sensor.
           GrFingerJava.startCapture(idSensor, this, this);
       } catch (GrFingerJavaException e) {
           //write error to log
           ui.writeLog(e.getMessage());
       }
   }

   /**
    * This function is called every time a fingerprint reader is unplugged.
    *
    * @see griaule.grFinger.StatusCallBack#onUnplug(java.lang.String)
    */
   public void onSensorUnplug(String idSensor) {
       //Logs the sensor has been unpluged.
       ui.writeLog("Sensor: "+idSensor+". Event: Unplugged.");
       try {
           GrFingerJava.stopCapture(idSensor);
       } catch (GrFingerJavaException e) {
           ui.writeLog(e.getMessage());
       }
   }

   /**
    * This function is called every time a fingerfrint image is captured.
    *
    * @see griaule.grFinger.ImageCallBack#onImage(java.lang.String, griaule.grFinger.FingerprintImage)
    */
   public void onImageAcquired(String idSensor, FingerprintImage fingerprint) {
       //Logs that an Image Event occurred.
       ui.writeLog("Sensor: "+idSensor+". Event: Image Captured.");

       //Stores the captured Fingerprint Image
       this.fingerprint=fingerprint;

       //Display fingerprint image
       ui.showImage(fingerprint);

       //now we have a fingerprint, the ONLY thing we can do is to extract the template.
       ui.enableImage();


       //If auto-extraction is enabled, let's extract the image.
       if (autoExtract) {
           extract();

           //If auto-Identify is also enabled, let's identify it.
           if (autoIdentify)
               identify();
       }

   }

   /**
    * This Function is called every time a finger is placed on sensor.
    *
    * @see griaule.grFinger.FingerCallBack#onFingerDown(java.lang.String)
    */
   public void onFingerDown(String idSensor) {
       // Just signals that a finger event ocurred.
       ui.writeLog("Sensor: "+idSensor+". Event: Finger Placed.");

   }

   /**
    * This Function is called every time a finger is removed from sensor.
    *
    * @see griaule.grFinger.FingerCallBack#onFingerUp(java.lang.String)
    */
   public void onFingerUp(String idSensor) {
       // Just signals that a finger event ocurred.
       ui.writeLog("Sensor: "+idSensor+". Event: Finger Removed.");

   }


   /**
    * Sets the colors used to paint templates.
    */
   public void setBiometricDisplayColors(
           Color minutiaeColor, Color minutiaeMatchColor,
           Color segmentColor, Color segmentMatchColor,
           Color directionColor, Color directionMatchColor)
   {
       try {
           // set new colors for BiometricDisplay
           GrFingerJava.setBiometricImageColors(
                   minutiaeColor,  minutiaeMatchColor,
                   segmentColor,   segmentMatchColor,
                   directionColor, directionMatchColor);

       } catch (GrFingerJavaException e) {
           //write error to log
           ui.writeLog(e.getMessage());
       }
   }

   /**
    * Returns a String containing information about the version of Fingerprint SDK being used.
    *
    * For instace:
    *      --------------------------------
    *      Fingerprint SDK version 5.0.
    *      The license type is 'Identification'.
    *      --------------------------------
    */
   public String getFingerprintSDKVersion() {
       try {
           return
               "Fingerprint SDK version " + GrFingerJava.getMajorVersion() + "." + GrFingerJava.getMinorVersion() + "\n" +
               "License type is '" + (GrFingerJava.getLicenseType() == GrFingerJava.GRFINGER_JAVA_FULL ? "Identification" : "Verification") + "'.";

       } catch (GrFingerJavaException e) {
           return null;
       }
   }

   /**
    * returns the current fingerprint image, without any biometric
    * information.
    */
   public BufferedImage getFingerprint() {
       return this.fingerprint;
   }

   /**
    * Saves the fingerprint image to a file using an ImageWriterSpi.
    * See ImageIO API.
    */
   public void saveToFile(File file, ImageWriterSpi spi) {
       try {
           //Creates a image writer.
           ImageWriter writer = spi.createWriterInstance();
           ImageOutputStream output = ImageIO.createImageOutputStream(file);
           writer.setOutput(output);

           //Writes the image.
           writer.write(fingerprint);

           //Closes the stream.
           output.close();
           writer.dispose();
       } catch (IOException e) {
           // write error to log
           ui.writeLog(e.toString());
       }

   }

   /**
    * Loads a fingerprint image from file using an ImageReaderSpi.
    * See ImageIO API.
    */
   public void loadFile(File file, int resolution, ImageReaderSpi spi) {
       try {
           //Creates a image reader.
           ImageReader reader = spi.createReaderInstance();
           ImageInputStream input = ImageIO.createImageInputStream(file);
           reader.setInput(input);
           //Reads the image.
           BufferedImage img = reader.read(0);
           //Close the stream
           reader.dispose();
           input.close();
           // creates and processes the fingerprint image
           onImageAcquired("File", new FingerprintImage(img, resolution));
       } catch (Exception e) {
           // write error to log
           ui.writeLog(e.toString());
       }
   }

   /**
    * Sets the parameters used for identifications / verifications.
    */
   public void setParameters(int identifyThreshold, int identifyRotationTolerance, int verifyThreshold, int verifyRotationTolorance) {
       try {
           fingerprintSDK.setIdentificationThreshold(identifyThreshold);
           fingerprintSDK.setIdentificationRotationTolerance(identifyRotationTolerance);
           fingerprintSDK.setVerificationRotationTolerance(verifyRotationTolorance);
           fingerprintSDK.setVerificationThreshold(verifyThreshold);

       } catch (GrFingerJavaException e) {
           //write error to log
           ui.writeLog(e.getMessage());
       }
   }

   /**
    * Returns the current verification threshold.
    */
   public int getVerifyThreshold() {
       try {
           //Try to get the parameters from Fingerprint SDK.
           return fingerprintSDK.getVerificationThreshold();
       } catch (GrFingerJavaException e) {
           //If fails to load the parameters, writes error to log and returns 0
           ui.writeLog(e.getMessage());
           return 0;
       }
   }

   /**
    * Returns the current rotation tolerance on verifications.
    */
   public int getVerifyRotationTolerance() {
       try {
           //Try to get the parameters from Fingerprint SDK.
           return fingerprintSDK.getVerificationRotationTolerance();
       } catch (GrFingerJavaException e) {
           //If fails to load the parameters, writes error to log and returns 0
           ui.writeLog(e.getMessage());
           return 0;
       }
   }

   /**
    * Returns the current identification threshold.
    */
   public int getIdentifyThreshold() {
       try {
           //Try to get the parameters from Fingerprint SDK.
           return fingerprintSDK.getIdentificationThreshold();
       } catch (GrFingerJavaException e) {
           //If fails to load the parameters, writes error to log and returns 0
           ui.writeLog(e.getMessage());
           return 0;
       }
   }

   /**
    * Returns the current rotation tolerance on identification.
    */
   public int getIdentifyRotationTolerance() {
       try {
           //Try to get the parameters from Fingerprint SDK.
           return fingerprintSDK.getIdentificationRotationTolerance();
       } catch (GrFingerJavaException e) {
           //If fails to load the parameters, writes error to log and returns 0
           ui.writeLog(e.getMessage());
           return 0;
       }
   }

   /**
    * Enables / Disables automatic fingerprint identification after
    * capture.
    *
    * As identification must be done after template extraction, this property
    * will only be effective if autoExtract if set to true.
    */
   public void setAutoIdentify(boolean state) {
       autoIdentify = state;
   }

   /**
    * Enables / Disables automatic fingerprint extraction after
    * capture.
    */
   public void setAutoExtract(boolean state) {
       autoExtract = state;
   }









   /**Connection to the Database.*/
   private Connection dbConnection;
   
   /**Statement used to insert templates on the DB.*/
   private PreparedStatement enrollStmt;
   
   /**Statement used to determine the Id of the last template inserted.*/
   private PreparedStatement insertedIdStmt;
   
   /**Statement used to retrieved all templates from the DB for identification.*/    
   private PreparedStatement identifyStmt;
   
   /**Statement used to retrieve the fingerprint template with a given ID for verification.*/
   private PreparedStatement verifyStmt;
   
   /**Statement used to remove all fingerprints from the DB.*/
   private PreparedStatement clearDbStmt;    
   
   
   
   
   
   /**
    * Initializes the connection to the database and the statements used to
    * insert/retrieve data from it.
    */
   private void initDB() {
       try {
           //Loads the ODBC driver. 
           Class.forName("org.hsqldb.jdbcDriver");

           // connect to a memory database
           dbConnection = DriverManager.getConnection("jdbc:hsqldb:mem:.","sa", "");

           // creates database table
           Statement stm = dbConnection.createStatement();
           stm.execute("CREATE TABLE enroll (id int identity primary key,template varbinary(1000))");
           stm.close();

           //Creates the statements that will be executed on the database,
           enrollStmt     = dbConnection.prepareStatement("INSERT INTO enroll(template) values(?)");
           insertedIdStmt = dbConnection.prepareStatement("SELECT MAX(ID) FROM enroll");
           identifyStmt   = dbConnection.prepareStatement("SELECT * FROM enroll");
           clearDbStmt    = dbConnection.prepareStatement("DELETE FROM enroll");
           verifyStmt     = dbConnection.prepareStatement("SELECT template FROM enroll WHERE ID=?");
           
           
           
       } catch (Exception e) {
           ui.writeLog("Error connecting to the database.");
       }
       
   }
   
   /**
    * Closes the connection to the database and frees any resources used.
    */
   private void destroyDB() {
       try {
           //Closes all the statements.
           enrollStmt.close();
           clearDbStmt.close();
           identifyStmt.close();
           verifyStmt.close();
           insertedIdStmt.close();
           
           //Closes the connection.
           dbConnection.close();
           
       } catch (Exception e) {
           e.printStackTrace();
       }        
   }
   
   
   /**
    * Add the current fingerprint template to the DB.
 * @throws IOException 
    */
   public void enroll() throws IOException {
       try {
           //Inserts the template on the database
           enrollStmt.setBinaryStream(1,new ByteArrayInputStream(template.getData()), template.getData().length);
           enrollStmt.executeUpdate();
           
           //Picks the ID generated for it. 
           ResultSet rs = insertedIdStmt.executeQuery();
           rs.next();
           ui.writeLog("Fingerprint enrolled with id = "+Integer.toString(rs.getInt(1)));
           
           PersonaHuellaDigital huella = new PersonaHuellaDigital();
           huella.setCodigoTipoDedo("C");
           huella.setCodigoTipoMano("I");
           huella.setIdPersona(1);
           huella.setObservacion("prueba de datos");
           ByteArrayInputStream dato = new ByteArrayInputStream(template.getData());
           byte[] buffer = new byte[8192];
           int bytesRead;
           ByteArrayOutputStream output = new ByteArrayOutputStream();
           while((bytesRead = dato.read(buffer)) != -1)
           {
        	   output.write(buffer, 0, bytesRead);
           }
           
           huella.setHuellaDigital(output.toByteArray());
           
           daoPersonaHuellaDigitalMapper.insert(huella);
           session.commit();
           
           
       } catch (SQLException e) {
           ui.writeLog("Error enrolling template");
       }
   }
   
   /**
    * Check current fingerprint against another one in the DB.
    */
   public void verify(int id) {
       try {
           //Gets the template with supplied ID from database.
           verifyStmt.setInt(1,id);
           ResultSet rs = verifyStmt.executeQuery();
           PersonaHuellaDigital huella = daoPersonaHuellaDigitalMapper.selectByPrimaryKey(id);
           
           if(huella != null)
           {
        	   byte templateBuffer[] = huella.getHuellaDigital();
        	   Template referenceTemplate = new Template(templateBuffer);
               
               //Compares the templates.
               boolean matched = fingerprintSDK.verify(template,referenceTemplate);
               if(matched)
               {
            	   ui.showImage(GrFingerJava.getBiometricImage(template, fingerprint, fingerprintSDK));
                   //Notifies the templates did match.
                   ui.writeLog("Coinciden con la base with score = " + fingerprintSDK.getScore() + ".");                    
               } else {
                   //Notifies the templates did not match.
                   ui.writeLog("NO COINCIDE not match with score = " + fingerprintSDK.getScore() + ".");
               }
           }
           
           
//           //Checks if the Id was found.
//           if (rs.next()){
//               //Reads the template data from the database.
//               byte templateBuffer[] = rs.getBytes("template");
//               //Creates a new Template
//               Template referenceTemplate = new Template(templateBuffer);
//               
//               //Compares the templates.
//               boolean matched = fingerprintSDK.verify(template,referenceTemplate);
//               
//               //If the templates match, display matching minutiae/segments/directions
//               if (matched){
//                   //displays minutiae/segments/directions that matched.
//                   ui.showImage(GrFingerJava.getBiometricImage(template, fingerprint, fingerprintSDK));
//                   //Notifies the templates did match.
//                   ui.writeLog("Matched with score = " + fingerprintSDK.getScore() + ".");                    
//               } else {
//                   //Notifies the templates did not match.
//                   ui.writeLog("Did not match with score = " + fingerprintSDK.getScore() + ".");
//               }
//               
//               //Notifies the ID has not been found.
//           } else {
//               ui.writeLog("The suplied ID does not exists.");
//           }
       } catch (SQLException e) {
           //write error to log            
           ui.writeLog("Error accessing database.");
       } catch (GrFingerJavaException e) {
           //write error to log
           ui.writeLog(e.getMessage());
       }
   }
   
   /**
    * Identifies the current fingerprint on the DB.
    */    
   public void identify() {
       try {
           //Starts identification process by supplying query template.
//           fingerprintSDK.prepareForIdentification(this.template);
//           
//           // Gets enrolled templates from database.
//           ResultSet rs = identifyStmt.executeQuery();
//           
//           // Iterate over all templates in database
//           while (rs.next()){
//               //Reads the current template data on a buffer
//               byte[] templateBuffer = rs.getBytes("template");
//               //And creates a new Template
//               Template referenceTemplate = new Template(templateBuffer);
//               
//               //Compares current template.
//               boolean matched = fingerprintSDK.identify(referenceTemplate);
//               
//               //If the templates match, display matching minutiae/segments/directions.
//               if (matched){
//                   //displays minutiae/segments/directions that matched.
//                   ui.showImage(GrFingerJava.getBiometricImage(template, fingerprint, fingerprintSDK));
//                   //Notifies the template was identified.
//                   ui.writeLog("Fingerprint identified. ID = "+rs.getInt("ID")+". Score = " + fingerprintSDK.getScore() + ".");
//                   
//                   //Stops searching
//                   return;
//               }
//           }
//           
//           //If all templates on the DB have been compared, and none of them
//           //match, notifies it has not been found.
//           ui.writeLog("Fingerprint not found.");
           
           
           
           
           
           
           
           
         //Starts identification process by supplying query template.
           fingerprintSDK.prepareForIdentification(this.template);
           
          
           
           // Iterate over all templates in database
           for(PersonaHuellaDigital huella : daoPersonaHuellaDigitalMapper.selectByExampleWithBLOBs(null)) {
               //Reads the current template data on a buffer
        	   
               byte[] templateBuffer = huella.getHuellaDigital();
               //And creates a new Template
               Template referenceTemplate = new Template(templateBuffer);
               
              
               
               //Compares current template.
               boolean matched = fingerprintSDK.identify(referenceTemplate);
               
               //If the templates match, display matching minutiae/segments/directions.
               if (matched){
                   //displays minutiae/segments/directions that matched.
                   ui.showImage(GrFingerJava.getBiometricImage(template, fingerprint, fingerprintSDK));
                   //Notifies the template was identified.
                   ui.writeLog("Fingerprint identified. ID = "+ huella.getIdHuella()+". Score = " + fingerprintSDK.getScore() + ".");
                   
                   //Stops searching
                   return;
               }
           }
           
           //If all templates on the DB have been compared, and none of them
           //match, notifies it has not been found.
           ui.writeLog("Fingerprint not found.");
           
           
//       } catch (SQLException e) {
//           //write error to log            
//           ui.writeLog("Error accessing database.");
       } catch (GrFingerJavaException e) {
           //write error to log
           ui.writeLog(e.getMessage());
       }
       
   }
   
   
   /**
    * Extract a fingerprint template from current image.
    */  
   public void extract() {
       
       try {
           //Extracts a template from the current fingerprint image. 
           template = fingerprintSDK.extract(fingerprint);
           
           //Notifies it has been extracted and the quality of the extraction
           String msg = "Template extracted successfully. ";
           //write template quality to log
           switch (template.getQuality()){
               case Template.HIGH_QUALITY:     msg +="High quality.";   break;
               case Template.MEDIUM_QUALITY:   msg +="Medium quality."; break;
               case Template.LOW_QUALITY:      msg +="Low quality.";    break;
           }
           ui.writeLog(msg);
           
           //Notifies the UI that template operations can be enabled.
           ui.enableTemplate();
           //display minutiae/segments/directions into image
           ui.showImage(GrFingerJava.getBiometricImage(template,fingerprint));
           
       } catch (GrFingerJavaException e) {
           //write error to log
           ui.writeLog(e.getMessage());
       }
       
   }
   
   /**
    * Removes all templates from the database.
    */
   public void clearDB() {
       try {
           //Executes the removal.
           clearDbStmt.executeUpdate();
           //Notifies it is now empty.
           ui.writeLog("Database is clear...");
           
       } catch (SQLException e) {
           //write error to log            
           ui.writeLog("Error accessing database.");
       }        
   }
   
   /**
    * Sets the directory where Fingerprint SDK's native libraries / license file are placed.
    */
   public static void setFingerprintSDKNativeDirectory(String nativeDirectory) {
       File directory = new File(nativeDirectory);
       
       try {
           GrFingerJava.setNativeLibrariesDirectory(directory);
           GrFingerJava.setLicenseDirectory(directory);
       } catch (GrFingerJavaException e) {
           e.printStackTrace(); 
       }
   }      
}
