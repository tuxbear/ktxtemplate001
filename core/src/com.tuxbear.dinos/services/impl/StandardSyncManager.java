package com.tuxbear.dinos.services.impl;

import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.services.impl.aws.responses.GameEventUpdatesResponse;

import java.util.*;

/**
 * Created by Ole - André Johansen on 12.02.14.
 */
public class StandardSyncManager implements SyncManager {

    private LocalStorage localStorage;
    private GameService gameService;

    public StandardSyncManager(LocalStorage localStorage, GameService gameService) {

        this.localStorage = localStorage;
        this.gameService = gameService;
    }

    @Override
    public void syncLocalStorageWithRemote(Runnable callback) {


        // detect if storage is empty

        // if empty, do full sync

        // if populated, do incremental update

        doIcrementalUpdate();


        callback.run();
    }

    private void doIcrementalUpdate() {

        // get date of last update
        Date dateOfLastUpdate = new Date();

        gameService.getUpdatesAsync(dateOfLastUpdate, new ServerCallback<GameEventUpdatesResponse>() {
            @Override
            public void processResult(GameEventUpdatesResponse result, ServerCallResults status) {
                // save results
            }
        });

    }
}
