package com.tuxbear.dinos.ui.widgets.controls;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ole - André Johansen on 13.01.14.
 */
public class ValueCheckboxGroup<T> extends ButtonGroup<ValueCheckbox<T>> {

    public List<T> getSelectedValues() {
        List<T> selectedValues = new ArrayList<T>();
        for(ValueCheckbox<T> btn : super.getAllChecked()) {
            selectedValues.add(btn.getValue());
        }

        return selectedValues;
    }

}
