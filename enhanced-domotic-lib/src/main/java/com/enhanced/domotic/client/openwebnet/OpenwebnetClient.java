package com.enhanced.domotic.client.openwebnet;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.enhanced.domotic.EnhancedDomotic;

public class OpenwebnetClient implements Runnable {
  
  private static final String ACK = "*#*1##";
  private static final String NACK = "*#*0##";
  private static final String SESSION_COMMAND = "*99*0##";
  //private static final String SESSION_EVENT = "*99*1##";
  
  private final ExecutorService executor = Executors.newFixedThreadPool(3);
  private final EnhancedDomotic<String> domotic;

  public OpenwebnetClient(EnhancedDomotic<String> domotic) {
    this.domotic = domotic;
  }

  @Override
  public void run() {
    
    List<String> command = domotic.command();
    System.out.println(command);
    
    
//    try (
//        Socket socket = new Socket(config.getHost(), config.getPort());
//        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
//      ) {
//        printWriter.write(val);
//        printWriter.print(val);
//        //printWriter.flush();
//        
//        // TODO buffer size
//        //BufferedWriter
//        //BufferedReader
//        
//        String response = bufferedReader.readLine();
//      } catch (IOException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
  }

}
