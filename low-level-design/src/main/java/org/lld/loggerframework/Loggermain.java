package org.lld.loggerframework;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

interface Logger {
   void log(LogLevel level, String messege);
}

class LogProvider{

   private static Logger logger;


   private LogProvider (){

   }
   public static Logger getLogger(LogConfig logConfig){
       if(logger==null){
        synchronized(LogProvider.class){
            if(logger==null){
                logger = new MyLogger(logConfig);
            }
        }

       }
       return logger;
   }

}

enum LogLevel{
    DEBUG, INFO, WARNING, ERROR, FATAL
}

interface LogMessage{
    String formate(LogLevel level , String message);
}

class LogMessageFormater implements LogMessage{
     
     @Override
    public String formate(LogLevel level , String message){
        return (" [ "+ LocalDateTime.now() +"]"+ "[ "+level + "] :" + message);
     }
}

//factory design pettern
interface LogDestination {
  void writeMessage(String message);
}

class MyLogger implements Logger {
      

     private LogLevel level;
     private List<LogDestination> LogDestination ;
     private LogMessage logMessage;

      public MyLogger(LogConfig config){
          this.level = config.getLogLevel();
          this.LogDestination = new ArrayList<>(config.getDestination());
          this.logMessage = config.getLoMessege();
      }

      @Override
      public void log(LogLevel level, String messege){
          for (LogDestination destination : LogDestination) {
                destination.writeMessage(level +": "+messege);
         }
        
      }

}

class ConsoleDestination implements LogDestination{

    @Override
   public void writeMessage(String message){
      System.out.println("Console: "+message);
   }
}

class FileDestination implements LogDestination{

    @Override
  public void writeMessage(String message){
      System.out.println("File: "+message);
   }
}
class DataBaseDestination implements LogDestination{

    @Override
   public void writeMessage(String message){
      System.out.println("Database: "+message);
   }
}

class DestinationFectory{

    public static LogDestination creteDestination(String destinationName){
         switch(destinationName.toLowerCase()){
            case "console": return new ConsoleDestination();
            case "file" : return new FileDestination();
            case "database" : return new DataBaseDestination();
            default : throw new IllegalArgumentException("Not Supported"); 
         }
    }
}


class LogConfig{
    private LogLevel level;
    private List<LogDestination> logDestination;
    private LogMessage logMessage;

     // constructor
     public LogConfig(LogLevel level, 
                     List<LogDestination> logDestination,
                    LogMessage logMessage)
    {
         this.level = level;
         this.logDestination = logDestination;
         this.logMessage = logMessage;
    }

    LogLevel getLogLevel() {return level;}

    List<LogDestination> getDestination() {
        return logDestination;
    }

    LogMessage getLoMessege(){
        return logMessage;
    }
    
}

public class Loggermain{
    public static void main(String[] args) throws InterruptedException {
        List<LogDestination> distinctions = new ArrayList<>();

        distinctions.add(DestinationFectory.creteDestination("console"));
          distinctions.add(DestinationFectory.creteDestination("File"));
            distinctions.add(DestinationFectory.creteDestination("Database"));
        
        LogConfig config = new LogConfig(LogLevel.DEBUG, distinctions, new LogMessageFormater());

        Logger logger = LogProvider.getLogger(config);
        
//        logger.log(LogLevel.DEBUG, "Debugging application");
//         logger.log(LogLevel.INFO, "Info application");
//          logger.log(LogLevel.ERROR, "Error application");
//           logger.log(LogLevel.WARNING, "warning application");

           Runnable logging = ()-> {
              for(int i= 1 ;i<=3;i++){
                  try {
                      logger.log(LogLevel.DEBUG, "Debugging Thread :"+ Thread.currentThread().getName());
                      Thread.sleep(100);

                  } catch (InterruptedException exception){
                      Thread.currentThread().interrupt();
                      logger.log(LogLevel.ERROR, "Thread interrupted" + exception.getMessage());
                  }
              }
           };
           Thread t1 = new Thread(logging);
           Thread t2 = new Thread(logging);
           t1.start();

           t2.start();
           Thread.sleep(300);
           t1.interrupt();
           t2.interrupt();

           try{
               t1.join();
               t2.join();
           } catch (InterruptedException exception){
               exception.printStackTrace();
           }


        
    }
}