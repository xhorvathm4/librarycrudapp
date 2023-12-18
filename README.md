# librarycrudapp
## Guide to Launch the Application:

### Backend
1. Open the directory "librarycrud" in IntelliJ Idea.
2. Run `src/main/java/com/mhexercise/librarycrud/LibrarycrudApplication.java`.

### Frontend
1. In the directory "librarycrud/frontend," run `npm install` in terminal.
2. In the directory "librarycrud/frontend," run `npm start` in terminal.
3. Subsequently, it's possible to enter the address `localhost:3000` in a browser.

### Application Configuration
Application configuration details such as login credentials, path to the logfile, etc. can be found in the file `src/main/resources/application.properties`.

The application utilizes an H2 database. Upon each launch, the data in it is deleted and reloaded from the file `src/main/resources/data.sql`.
To view the data, access it via a browser at the address `http://localhost:8080/h2-console` after entering the details configured in the `application.properties` file.

Tested with IntelliJ IDEA 2023.3.1