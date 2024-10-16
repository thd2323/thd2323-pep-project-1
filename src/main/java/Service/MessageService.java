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

    public Message updateMessageID(Message message) {
        if(messageDAO.getMessageID(message.getMessage_id()) == null){
            return null;
        }
        messageDAO.updateMessageID(message);
        return messageDAO.getMessageID(message.getMessage_id());
    }

    public Message addMessage(Message message){
        return messageDAO.addMessage(message);
    }

    public Message deleteMessage(Message message){
        return messageDAO.deleteMessage(message);
    }

    public Message getMessageID(int i){
        return messageDAO.getMessageID(i);
    }

}
