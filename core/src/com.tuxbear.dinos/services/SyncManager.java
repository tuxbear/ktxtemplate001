package com.tuxbear.dinos.services;

import java.io.IOException;

/**
 * User: tuxbear Date: 28/01/14 Time: 12:48
 */
public interface SyncManager {

    void syncLocalStorageWithRemote(Runnable callback) throws IOException;

}
