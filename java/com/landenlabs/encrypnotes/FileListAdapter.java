package com.landenlabs.encrypnotes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.landenlabs.encrypnotes.ui.UiUtil;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Support 'data model' adapter used with Dialog class to 
 * provided a selectable file list.
 * 
 * @author Dennis Lang
 *
 */
public class FileListAdapter extends ArrayAdapter<String> implements View.OnClickListener,  View.OnLongClickListener  {

    private final File m_storagePath;
    private final String m_ext;
    private final DateFormat m_dateFormat;
    private final int m_dialogHeight;
    
    /**
     * File List Adapter - Present list of file names with modify date and file length.
     * 
     * @param context
     *      UI Context
     * @param storagePath
     *      Optional path to file names provided
     * @param ext
     *      Optional extension to add to file to find file info.
     * @param dateFormat
     *      How to format date
     * @param dialogHeight
     *      Maximum height of dialog. Rows will be expanded to fill space. 
     */
    public FileListAdapter(Context context, File storagePath, String ext, DateFormat dateFormat, int dialogHeight) {
        super(context, R.layout.file_list_row, R.id.fl_name, new ArrayList<String>());
        m_storagePath = storagePath;
        m_ext = ext;
        m_dateFormat = dateFormat;
        m_dialogHeight = dialogHeight;
    }

    AdapterView.OnItemLongClickListener m_onItemLongClickListener;
    public void setOnItemLongClickListener( AdapterView.OnItemLongClickListener longClickList) {
        m_onItemLongClickListener = longClickList;
    }

    AdapterView.OnItemClickListener m_onItemClickListener;
    public void setOnItemClickListener( AdapterView.OnItemClickListener clickList) {
        m_onItemClickListener = clickList;
    }

    public void deleteFile(final String filename) {
        int pos = this.getPosition(filename);

        try {
            File file = new File(m_storagePath, filename + m_ext);
            file.delete();
            this.remove(filename);
        } catch (Exception ex) {
            Toast.makeText(this.getContext(), "Failed to delete file " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void renameFile(final String fromFilename, final String toFilename) {
        int pos = this.getPosition(fromFilename);

        try {
            File fromFile = new File(m_storagePath, fromFilename + m_ext);
            File toFile = new File(m_storagePath, toFilename + m_ext);
            if (fromFile.renameTo(toFile)) {
                this.remove(fromFilename);
                this.add(toFilename);
                this.sort();
            } else {
                Toast.makeText(this.getContext(), "Failed to rename file ", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this.getContext(), "Failed to rename file " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public File getFile(final String filename) {
        File file = null;
        try {
            file = new File(m_storagePath, filename + m_ext);
        } catch (Exception ex) {
            Toast.makeText(this.getContext(), "Failed to access file " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return file;
    }

    public String getDateStr(final File file) {
        return m_dateFormat.format(file.lastModified());
    }

    public String getInfoStr(final File file) {
        StringBuilder sb = new StringBuilder();

        Doc doc = new Doc();
        try {
            doc.doOpen(file, null);
            sb.append("\nVersion: ").append(doc.getVersion());
            sb.append("\nHint: ").append(doc.getHint());

            // Doc Meta not available unless you have password.
            // Doc.DocMetadata docMetaData = doc.getDocMetadata();
            // sb.append(Doc.getInfoStr(docMetaData, m_dateFormat));
        } catch (Exception ex) {
        }

        return sb.toString();
    }

    public void sort() {
        super.sort(String.CASE_INSENSITIVE_ORDER);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup itemView = (ViewGroup) super.getView(position, convertView, parent);
        String item = getItem(position);

        itemView.setTag(Integer.valueOf(position));

        // Once you set onLongClickListener it breaks the default onClick
        // behavior and requires that both click actions get handled via
        // custom code.
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        /* Does not work - shrinks normal rows.
        int rowHeight = m_dialogHeight / (this.getCount()+2);
        if (itemView.getHeight() < rowHeight) {
            // If full list does not fill screen, increase padding to use available space. 
            AbsListView.LayoutParams params = (AbsListView.LayoutParams) itemView.getLayoutParams();
            params.height = rowHeight;
            itemView.setLayoutParams(params);
        }
        */
        
        if (1 == (position & 1))
            itemView.setBackgroundColor(0xffffffff);
        else
            itemView.setBackgroundColor(0xffd0d0d0);

        if (itemView.isSelected())
            itemView.setBackgroundColor(0xffff8080);
        
        // TextView nameTv = (TextView) itemView.findViewById(R.id.fl_name);
        File file = new File(m_storagePath, item + m_ext);

        TextView hintTv = UiUtil.viewById(itemView, R.id.fl_hint);
        TextView dateTv = UiUtil.viewById(itemView, R.id.fl_date);
        TextView vernTv = UiUtil.viewById(itemView, R.id.fl_version);
        TextView sizeTv = UiUtil.viewById(itemView, R.id.fl_size);
        
        if (file.exists()) {
            // Get Hint and version.
            String hint = "";
            String version = "";
            Doc doc = new Doc();
            try {
                doc.doOpen(file, null);
                hint = doc.getHint();
                version = doc.getVersion();
            } catch (Exception ex) {
            }

            dateTv.setText(m_dateFormat.format(file.lastModified()));
            sizeTv.setText(String.format("%,d", file.length()));
            hintTv.setText(hint);
            vernTv.setText(version);
        } else {
            dateTv.setText("");
            sizeTv.setText("");
            vernTv.setText("");
            hintTv.setText("");
        }
            
        return itemView;
    }

    // ============================================================================================
    // View.OnClickListener
    @Override
    public void onClick(View view) {
        int pos = ((Integer)view.getTag()).intValue();
        if (m_onItemClickListener != null)
            m_onItemClickListener.onItemClick(null, view, pos, -1);

    }
    // ============================================================================================
    // View.OLongClickListener
    @Override
    public boolean onLongClick(View view) {
        int pos = ((Integer)view.getTag()).intValue();
        if (m_onItemLongClickListener != null)
            return m_onItemLongClickListener.onItemLongClick(null, view, pos, -1);

        return true;
    }
}
