package by.brzmath.app.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String userIdPrincipal;
    private String social;
    private int solvedTasks;
    private int createdTasks;
    private Date firstDate;
    private Date lastDate;
    private boolean blocked;

    public String getFormatFirstDate(){
        return (new SimpleDateFormat("yyyy.MM.dd").format(firstDate)).toString();
    }

    public int getSolvedTasks() {
        return solvedTasks;
    }

    public void setSolvedTasks(int solvedTasks) {
        solvedTasks = solvedTasks;
    }

    public int getCreatedTasks() {
        return createdTasks;
    }

    public void setCreatedTasks(int createdTasks) {
        createdTasks = createdTasks;
    }


}
