package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("messages", this::getAllMessagesHandler);
        app.post("register", this::postAccountHandler);
        app.post("messages", this::postMessageHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.get("messages/{message_id}", this::getMessageID);
        app.patch("messages/{message_id}", this::updateMessageID);
        return app;
    }

    private void updateMessageID(Context ctx) throws JsonProcessingException {
        
        
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        System.out.println("test message");
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        
        
        System.out.println("id is " + id);
        String text = message.getMessage_text();
        System.out.println("text is " + text);
        
        Message message2 = messageService.updateMessageID(id, text);
        if(text.isBlank() || text.isEmpty() || text.equals(null) || text.length() > 255 || message2 == null){
            ctx.status(400);
            return;
        }
        else{
            
        //ctx.status(200);
        ctx.json(message2);
        }
    }

    private void getMessageID(Context ctx) throws JsonProcessingException {//grabs a messge by its id
        ObjectMapper mapper = new ObjectMapper();
        //Message message = mapper.readValue(ctx.body(), Integer.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageID(message_id);
        if(message == null){
            ctx.status(200);
            return;
        }
        else{
        ctx.status(200);
        ctx.json(message);
        }
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message removedMessage = messageService.deleteMessage(id);
        if(removedMessage == null){
            ctx.status(200);
            return;
        }
        ctx.json(removedMessage);
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        String use = account.getUsername();
        String pass = account.getPassword();
        if(use == null || pass == null || use.isEmpty() || use.equals(" ")){
            ctx.status(400);
            return;
        }
        if(pass.length() < 4){
            ctx.status(400);
            return;
        }
        else{
            Account addedAccount = accountService.addAccount(account);
            if(addedAccount!=null){
                ctx.json(mapper.writeValueAsString(addedAccount));
            }else{
                ctx.status(400);
            }
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        System.out.println("default message");//delete this
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        String text = message.getMessage_text();
        
        
        if(text == null || text.isEmpty() || text.length() > 255 || text.equals(" ")){//need to check if user is legit
            System.out.println("it was triggered");//delete this
            ctx.status(400);
            return;
        }
       
        
        
            Message addedMessage = messageService.addMessage(message);
            if(addedMessage!=null){
                ctx.json(mapper.writeValueAsString(addedMessage));
            }else{
                ctx.status(400);
            }
        
    }


    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}