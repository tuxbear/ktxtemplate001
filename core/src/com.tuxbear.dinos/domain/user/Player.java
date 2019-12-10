package com.tuxbear.dinos.domain.user;

import com.tuxbear.dinos.utils.SerializableDate;

import java.util.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 08/12/13 Time: 12:52 To change this template use File | Settings | File
 * Templates.
 */
public class Player {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SerializableDate getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(SerializableDate lastSeen) {
        this.lastSeen = lastSeen;
    }

    public List<String> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<String> friendIds) {
        this.friendIds = friendIds;
    }

    public List<String> getBlocked() {
        return blocked;
    }

    public void setBlocked(List<String> blocked) {
        this.blocked = blocked;
    }

    String id;

    SerializableDate lastSeen;

    private long xp;

    List<String> friendIds;

    List<String> blocked;

    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        this.xp = xp;
    }

    public Player() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (xp != player.xp) return false;
        if (blocked != null ? !blocked.equals(player.blocked) : player.blocked != null) return false;
        if (friendIds != null ? !friendIds.equals(player.friendIds) : player.friendIds != null) return false;
        if (id != null ? !id.equals(player.id) : player.id != null) return false;
        if (lastSeen != null ? !lastSeen.equals(player.lastSeen) : player.lastSeen != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lastSeen != null ? lastSeen.hashCode() : 0);
        result = 31 * result + (int) (xp ^ (xp >>> 32));
        result = 31 * result + (friendIds != null ? friendIds.hashCode() : 0);
        result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
        return result;
    }
}
