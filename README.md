# ControlPalabradeVida
 ![Sistema de gestión de Usuarios](/Documentacion/Finger_reader.jpg)
 Sistema de control y seguimiento de asistencia a actividades academicas en el instituto biblico **Palabra de Vida Cochabamba**
 System of control and monitoring of assistance to academic activities in the biblical institute **Palabra de Vida Cochabamba**



 
 ### System Modules
 - User Authentication.- Login for acces
 
 ![Sistema de gestión de Usuarios](/Documentacion/loguin.png)
 
- Person Registration.- Register data information about the students

![Sistema de registro de personas](/Documentacion/person.png)

- Fingerprint Registration - You can add thre different fingers or the same finger

![Modulo de lectura y registro de huellas digitales](/Documentacion/digital_fingers.png)

- Events Registration.- Add all the activities you want to control, you can configure a time before the event stars and you can add an extra time to penalize delays 

![Sistema de gestión de Usuarios](/Documentacion/events.png)

- Permission Registration.- You can register permission for students when they have an extra activities outside of the Institute, that does not allowed them to be present in the event.

![Sistema de gestión de Usuarios](/Documentacion/permision.png)

- Reporting by Person.- You can search a student and get the information about and academic activitie or event.

![Sistema de gestión de Usuarios](/Documentacion/reporting1.png)

- General Report by Person for all events.- You can see all the records of the entries in time, delay, absences or permissions of all the activities of a student 

![Sistema de gestión de Usuarios](/Documentacion/reporting2.png)

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

