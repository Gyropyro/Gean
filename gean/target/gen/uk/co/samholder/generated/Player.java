
package uk.co.samholder.generated;

import java.util.Objects;


/**
 * A player.
 * 
 */
public class Player {

    private String username;
    private int score;
    private long lastLogin;

    /**
     * Sets the Player's username.
     * 
     * @param username
     *     the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the Player's username.
     * 
     * @return
     *     username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the Player's score.
     * 
     * @param score
     *     the score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the Player's score.
     * 
     * @return
     *     score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the Player's last login.
     * 
     * @param lastLogin
     *     the last login.
     */
    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Gets the Player's last login.
     * 
     * @return
     *     last login.
     */
    public long getLastLogin() {
        return lastLogin;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (getClass()!= object.getClass()) {
            return false;
        }
        Player other = ((Player) object);
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (this.score!= other.score) {
            return false;
        }
        if (this.lastLogin!= other.lastLogin) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = ((79 *hash)+ Objects.hashCode(this.username));
        hash = ((79 *hash)+ Objects.hashCode(this.score));
        hash = ((79 *hash)+ Objects.hashCode(this.lastLogin));
        return hash;
    }

}
