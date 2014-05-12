
package chat;
 
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static int i=-1; 
    public static void main(String[] arg) throws IOException {
        
        ArrayList<DataOutputStream> sockets = new ArrayList<DataOutputStream>();
        ArrayList<String> nicknames = new ArrayList<String>();
        String nickname = null;
        ServerSocket ss = null; Socket cs = null;
        Scanner sc = new Scanner(System.in); 
        
        ss = new ServerSocket(7);  
        System.out.println("Server is on.");
        
        while (true) {
            cs = ss.accept();
            DataInputStream is = new DataInputStream(cs.getInputStream());  
            DataOutputStream os = new DataOutputStream(cs.getOutputStream());
            int nicknameValabil=0;
          
            while(nicknameValabil==0)
            {
                nickname = is.readUTF();
                if(nickname.length()>0)
                {
                    if(nicknames.contains(nickname)) 
                    os.writeUTF("Nickname "+nickname+" already used.\nTry again:");
                    else { nicknameValabil=1; os.writeUTF("Nickname available."); os.writeUTF("You are connected to server."); }
                }
            }
            
            sockets.add(os);
            nicknames.add(nickname);
            System.out.println("\nNew user: "+nickname);
            
            new Connection(cs,++i,sockets,nicknames);
            }
        } 
}

class Connection extends Thread {
    
    int index;  String message = null; String thisnickname = "";
    Socket cs = null; DataInputStream is = null;  DataOutputStream os =null  ;
    ArrayList<DataOutputStream> sockets; ArrayList<String> nicknames;
  
    public Connection(Socket client, int i,ArrayList<DataOutputStream> sockets,ArrayList<String> nicknames) throws IOException 
  {
        cs = client; index = i;  thisnickname = nicknames.get(index);
        is = new DataInputStream(cs.getInputStream());  
        os = new DataOutputStream(cs.getOutputStream());
        this.sockets = sockets;  
        this.nicknames = nicknames;    
     start(); 
  }
 
     int numberOfWords(String message)
    {
        int words; 
        String trimmed = message.trim();
        words = trimmed.isEmpty() ? 0 : trimmed.split(" ").length;
        return words;
    }
    
    void LIST()
  {
    
      try {
          os.writeUTF("Users online:");
          for(int i=0;i<nicknames.size();i++) 
          os.writeUTF(nicknames.get(i));
          
          os.writeUTF(""); 
          }
     catch(IOException e) { }
  }
    
  void MSG(String nickname,String message) throws IOException 
  {
      if(nicknames.contains(nickname))
      {
          int receiver = nicknames.indexOf(nickname);
          sockets.get(receiver).writeUTF(thisnickname+": "+message); 
      }
      else os.writeUTF("User "+nickname+" is not online or the command is not right.");
  }
  
  void BCAST(String message) throws IOException 
 {
     int ID = nicknames.indexOf(thisnickname);
     
     for(int i=0;i<sockets.size();i++)
            if(i!=ID)
                sockets.get(i).writeUTF(thisnickname+"(BCAST): "+message);
 }
 
void NICK(String newnickname) throws IOException
{
    int ID = nicknames.indexOf(thisnickname);
    
    if(nicknames.contains(newnickname))
        os.writeUTF("Nickname "+newnickname+" already used.");
    else { nicknames.set(ID, newnickname); thisnickname = newnickname; }
}
  
  public void run()
  {
      try {
          int run=1;
          while (run==1) { 
              
              index = nicknames.indexOf(thisnickname);
              message = is.readUTF();
           
                  
              if(message.length()>0)
                { 
                        System.out.println(message);
                        if(message.equals("LIST")) LIST();
                        else
                         if(message.length()>=3 && message.substring(0,3).equals("MSG") && numberOfWords(message)>2)  
                           MSG(message.substring(4,message.indexOf(" ",4)),message.substring(message.indexOf(" ",4)+1));
                        else
                         if(message.length()>=5 && message.substring(0,5).equals("BCAST") && numberOfWords(message)>1) 
                             BCAST(message.substring(6)); 
                        else
                                if(message.length()>=4 && message.substring(0,4).equals("NICK") && numberOfWords(message)>1) 
                                    NICK(message.substring(5));
                            else 
                                    if(message.equals("QUIT")) 
                                    {
                                        sockets.remove(index);
                                        nicknames.remove(index);
                                        is.close();
                                        os.close();
                                        cs.close();
                                        run=0;
                                        Server.i--;
                                    }
                        else
                                        sockets.get(index).writeUTF("Command is not right.");
                }
          }
        }
       
      catch(IOException e) { }
    }
}   
