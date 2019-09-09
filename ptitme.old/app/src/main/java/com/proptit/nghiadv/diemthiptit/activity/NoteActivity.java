package com.proptit.nghiadv.diemthiptit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.widget.EditText;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.model.Note;
import com.proptit.nghiadv.diemthiptit.database.NoteSupport;

public class NoteActivity extends BaseLayoutActivity {
    EditText editText;
    Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        editText = (EditText) findViewById(R.id.ed_content);
        hideActivityLogo();
        setActivityTitle("Note");

        AppBarLayout appBarLayout = (AppBarLayout) coordinatorLayout.findViewById(R.id.appbar_layout);
        appBarLayout.setBackgroundColor(getResources().getColor(R.color.backround_cell));

        Intent intent = getIntent();
        currentNote = (Note) intent.getSerializableExtra("note");
        editText.setText(currentNote.getContent());
        editText.setSelection(editText.getText().length());

    }

    @Override
    protected Boolean isAppUseDrawerLayout() {
        return false;
    }

    @Override
    protected Boolean isUseToolbar() {
        return true;
    }


    private void updateContent() {
        NoteSupport noteSupport = new NoteSupport(this);
        String content = editText.getText().toString();
        currentNote.setContent(content);
        noteSupport.updateNote(currentNote);
    }

    @Override
    public void onBackPressed() {
        updateContent();
        super.onBackPressed();
    }
}
