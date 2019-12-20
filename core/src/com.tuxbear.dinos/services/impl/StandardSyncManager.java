package com.tuxbear.dinos.services.impl;

import com.tuxbear.dinos.domain.dto.responses.GameEventUpdatesResponse;
import com.tuxbear.dinos.services.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by Ole - André Johansen on 12.02.14.
 */
public class StandardSyncManager implements SyncManager {

    private LocalStorage localStorage;
    private DataService dataService;

    public StandardSyncManager(LocalStorage localStorage, DataService dataService) {

        this.localStorage = localStorage;
        this.dataService = dataService;
    }

    @Override
    public void syncLocalStorageWithRemote(Runnable callback) throws IOException {


        // detect if storage is empty

        // if empty, do full sync

        // if populated, do incremental update

        doIcrementalUpdate();


        callback.run();
    }

    private void doIcrementalUpdate() throws IOException {

        // get date of last update
        Date dateOfLastUpdate = new Date();

        dataService.getUpdatesAsync(dateOfLastUpdate, new ServerCallback<GameEventUpdatesResponse>() {
            @Override
            public void processResult(GameEventUpdatesResponse result, ServerCallResults status) {
                // save results
            }
        });

    }
}
