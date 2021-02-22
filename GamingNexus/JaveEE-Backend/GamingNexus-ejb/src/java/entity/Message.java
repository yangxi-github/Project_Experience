/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import util.exception.MessageTypeException;

/**
 *
 * @author root
 */
@Entity
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageID;
    @Past
    @NotNull
    private Date dateTimeStamp;
    @NotNull
    @Size(min = 1, max = 2000)
    private String message;
    private List<String> multimediaURL;
    @NotNull
    @OneToOne
    private User fromUser;
    @NotNull
    private boolean isNewPost = false;

    @OneToOne
    private Forum forum;

    @OneToOne
    private Chat chat;

    //TODO: Make sure that the Message entity is NOT linked to both Chat entity and Forum Entity
    public Message() {

    }

    public Message(Date dateTimeStamp, String message, List<String> multimediaURL, User fromUser, Forum forum) {
        this();
        this.dateTimeStamp = dateTimeStamp;
        this.message = message;
        this.multimediaURL = multimediaURL;
        this.fromUser = fromUser;
        this.forum = forum;
    }//constructor for forum message

    public Message(Date dateTimeStamp, String message, List<String> multimediaURL, User fromUser, Chat chat) {
        this();
        this.dateTimeStamp = dateTimeStamp;
        this.message = message;
        this.multimediaURL = multimediaURL;
        this.fromUser = fromUser;
        this.chat = chat;
    }//constructor for chat message

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageID != null ? messageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the messageID fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messageID == null && other.messageID != null) || (this.messageID != null && !this.messageID.equals(other.messageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.Message[ id=" + messageID + " ]";
    }

    /**
     * @return the dateTimeStamp
     */
    public Date getDateTimeStamp() {
        return dateTimeStamp;
    }

    /**
     * @param dateTimeStamp the dateTimeStamp to set
     */
    public void setDateTimeStamp(Date dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the multimediaURL
     */
    public List<String> getMultimediaURL() {
        return multimediaURL;
    }

    /**
     * @param multimediaURL the multimediaURL to set
     */
    public void setMultimediaURL(List<String> multimediaURL) {
        this.multimediaURL = multimediaURL;
    }

    /**
     * @return the fromUser
     */
    public User getFromUser() {
        return fromUser;
    }

    /**
     * @param fromUser the fromUser to set
     */
    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    /**
     * @return the isNewPost
     */
    public boolean isIsNewPost() {
        return isNewPost;
    }

    /**
     * @param isNewPost the isNewPost to set
     */
    public void setIsNewPost(boolean isNewPost) {
        this.isNewPost = isNewPost;
    }

    /**
     * @return the forum
     */
    public Forum getForum() {
        return forum;
    }

    /**
     * @param forum the forum to set
     * @throws util.exception.MessageTypeException
     */
    public void setForum(Forum forum) throws MessageTypeException{
        if (this.chat == null) {
            this.forum = forum;
        } else {
            throw new MessageTypeException("This is a forum message already");
        }
    }

    /**
     * @return the chat
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * @param chat the chat to set
     * @throws util.exception.MessageTypeException
     */
    public void setChat(Chat chat) throws MessageTypeException {
        if (this.forum == null) {
            this.chat = chat;
        } else {
            throw new MessageTypeException("This is a forum message already");
        }
    }

}
