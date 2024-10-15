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
        app.delete("messages", this::deleteMessageHandler);
        return app;
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message removedMessage = messageService.deleteMessage(message);
        if(removedMessage == null){
            ctx.status(400);
            return;
        }
        ctx.json(message);
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