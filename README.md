# Sistema de Control de Asistencias

 <p align="center">
  <img src="/Documentacion/Finger_reader.jpg">
</p>
 
 Sistema de control y seguimiento de asistencia a actividades academicas en el instituto biblico **Palabra de Vida Cochabamba**
 
 System of control and monitoring of assistance to academic activities in the biblical institute **Palabra de Vida Cochabamba**



 
 ### System Modules
 - User Authentication.- Login for acces
 
 <p align="center">
  <img src="/Documentacion/loguin.png">
</p>
 
- Person Registration.- Register data information about the students


 <p align="center">
  <img src="/Documentacion/person.png">
</p>

- **Fingerprint Registration** - You can add thre different fingers or the same finger


<p align="center">
  <img src="/Documentacion/digital_fingers.png">
</p>

- Events Registration.- Add all the activities you want to control, you can configure a time before the event stars and you can add an extra time to penalize delays 

<p align="center">
  <img src="/Documentacion/events.png">
</p>

- Permission Registration.- You can register permission for students when they have an extra activities outside of the Institute, that does not allowed them to be present in the event.

<p align="center">
  <img src="/Documentacion/permision.png">
</p>

- Control of Activities.- When the activity is running on the scheduled time, the students have to put their finger to be registered on the event

<p align="center">
  <img src="/Documentacion/control.png">
</p>

- Reporting by Person.- You can search a student and get the information about and academic activitie or event.

<p align="center">
  <img src="/Documentacion/reporting1.png">
</p>

- General Report by Person for all events.- You can see all the records of the entries in time, delay, absences or permissions of all the activities of a student 


<p align="center">
  <img src="/Documentacion/reporting2.png">
</p>

### FingerPrint Reader Models suportss

![Sistema de gestión de Usuarios](/Documentacion/Digital_Lector_4500.png)
![Sistema de gestión de Usuarios](/Documentacion/Digital_Lector_4000B.png)

### Tecnologies
- Programming Languaje : **JAVA**
- Data Base : **Postgres**
- IDE : **Eclipse**
- Data Base Managent : **PgAdmin**
- Reporting : **IReport**
- Orm : **Mybatis**
*- SKD for FingerPrint Reading : **Fingerprint SDK Pack [Verifinger DigitalPersona Griaule ZKFinger]**

### Java Librearies
- Mybatis
- DateChooser
- GrFingerJava
- Groovy
- Hibernate
- JasperReport
- jCalendar
- postgresql

### Screen Captures


### How to install
You have to install JDK from java, then install postgres database managent and restore de database backup.
You have to install de SDK of *DigitalPersona Griaule ZKFinger* and the respective drivers of the digital reader.

You can change the configuration file for connection to the database in the [configuracionInicial.properties](https://github.com/toitolucho/Trabajo-Social-San-Juan-Dios/blob/main/Proyecto/src/org/quarkbit/trabajosocialsanjuan/dao/config/configuracionInicial.properties "configuracionInicial.properties") file at the **dao** folder

Remember to put the right credentials that you configured in the installation procces of Postgres.

