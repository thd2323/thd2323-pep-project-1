package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;


public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    //get all messages
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesID(int id) {
        return messageDAO.getAllMessagesID(id);
    }

    public Message updateMessageID(int id, String text) {
        if(messageDAO.getMessageID(id) == null){
            System.out.println("message service returning null");
            return null;
        }
        messageDAO.updateMessageID(id, text);
        return messageDAO.getMessageID(id);
    }

    public Message addMessage(Message message){
        return messageDAO.addMessage(message);
    }

    public Message deleteMessage(int id){
        Message m = messageDAO.getMessageID(id);
        if(m == null){
            return null;
        }
        messageDAO.deleteMessageID(id);
        return m;
    }

    public Message getMessageID(int i){
        return messageDAO.getMessageID(i);
    }

}
