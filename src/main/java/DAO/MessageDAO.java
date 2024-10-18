package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessage(Message message){
        
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            //Write SQL logic here
            String sql = "Select FROM message (message_id, posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getMessage_id());
            preparedStatement.setInt(2, message.getPosted_by());
            preparedStatement.setString(3, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message2 = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                        return message2;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessageID(int i){
        Connection connection = ConnectionUtil.getConnection();
        System.out.println("getMessageID trying find message by id " + i);
        try {
            //Write SQL logic here
            String sql = "Select * FROM message WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, i);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message2 = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                        return message2;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("get message by id returned null");
        return null;
    }

    public void updateMessageID(int id, String text){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            //Write SQL logic here
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
           
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    /* 
    public Message deleteMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            
            String sql = "DELETE FROM message (message_id, posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message.getMessage_id());
            preparedStatement.setInt(2, message.getPosted_by());
            preparedStatement.setString(3, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
          
                return message;
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    */

    public void deleteMessageID(int id){
        System.out.println("deleting method begun id is " + id);
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            
            String sql = "DELETE FROM message WHERE message_id = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, id);
            

            preparedStatement.executeUpdate();
          
               
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
      
    }

    public Message addMessage(Message message){
        
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id,message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
