package com.tuxbear.dinos.ui.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.ui.widgets.controls.ValueCheckbox;
import com.tuxbear.dinos.ui.widgets.controls.ValueCheckboxGroup;

import java.io.IOException;


/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 06/01/14 Time: 15:36 To change this template use File | Settings | File
 * Templates.
 */
public class CreateGameDialog extends AbstractCallbackDialog {
    private PlayerService playerService = IoC.resolve(PlayerService.class);
    private DataService dataService = IoC.resolve(DataService.class);
    private Logger logger = IoC.resolve(Logger.class);


    public CreateGameDialog(Skin skin) {
        super("New game", skin);
        final CreateGameDialog self = this;
        final SelectBox<String> boardSelect = new SelectBox<String>(skin);
        boardSelect.setItems("Playground");

        final SelectBox<String> difficultySelect = new SelectBox<>(skin);
        difficultySelect.setItems("easy", "normal", "hard");

        Table friendSelectList = new Table(skin);

        final ValueCheckboxGroup<String> selectedFriendsGroup = new ValueCheckboxGroup<>();
        selectedFriendsGroup.setMaxCheckCount(10);
        selectedFriendsGroup.setMinCheckCount(1);
        for(String friendUsername : playerService.getCurrentPlayer().getFriendIds()){
            ValueCheckbox friendSelected = new ValueCheckbox("", skin, friendUsername);
            selectedFriendsGroup.add(friendSelected);
            friendSelectList.add(friendSelected);
            friendSelectList.add(friendUsername);
            friendSelectList.row();
        }
        friendSelectList.pack();

        final Table contentTable = getContentTable();
        contentTable.add("Board");
        contentTable.add(boardSelect);
        contentTable.row();
        contentTable.add("Difficulty");
        contentTable.add(difficultySelect);
        contentTable.row();
        contentTable.add("Opponents").colspan(2);
        contentTable.row();
        contentTable.add(friendSelectList).colspan(2).center();
        contentTable.row();

        final TextButton backButton = new TextButton("<<", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                self.hide();
            }
        });

        final TextButton createButton = new TextButton("GO!", skin);

        createButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                final LoadingDialog loadingDialog = new LoadingDialog(ResourceContainer.skin, "Creating game...");
                loadingDialog.show(getStage());

                try {
                    dataService.createGameAsync(
                            playerService.getCurrentPlayer().getUsername(),
                            selectedFriendsGroup.getSelectedValues(),
                            boardSelect.getSelection().toString(),
                            9,
                            difficultySelect.getSelection().toString(),
                            new ServerCallback<MultiplayerGame>() {
                                @Override
                                public void processResult(MultiplayerGame result, ServerCallResults status) {
                                    if (status.getStatus() == ServerCallStatus.SUCCESS) {
                                        hide();
                                        loadingDialog.hide();
                                        result(result);
                                    } else {
                                        loadingDialog.hide();
                                        logger.log(status.getFailureString());
                                    }
                                }
                            }
                    );
                } catch (IOException e) {
                    //TODO: notification and handling
                }
            }
        });

        contentTable.add(backButton);
        contentTable.add(createButton);
    }
}
