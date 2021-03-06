package org.jpos.qi.minigl;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.themes.ValoTheme;
import org.jpos.ee.DB;
import org.jpos.gl.GLSession;
import org.jpos.gl.Journal;
import org.jpos.qi.QI;

import java.util.List;

public class JournalsCombo extends ComboBox<Journal> {
    /**
     * Create and fill journals combo
     */
    public JournalsCombo (boolean required) {
        super(QI.getQI().getMessage("journal"));
        setItemCaptionGenerator(Journal::getName);
        List<Journal> journals = getJournals();
        if (journals != null)
            setItems(journals);
        setStyleName(ValoTheme.COMBOBOX_SMALL);
        setEmptySelectionAllowed(!required);
    }

    private List<Journal> getJournals () {
        try {
            return (List<Journal>) DB.exec(db -> {
                GLSession session = new GLSession(db);
                return session.getAllJournals();
            });
        } catch (Exception e) {
            QI.getQI().getLog().error(e);
            return null;
        }
    }
}
