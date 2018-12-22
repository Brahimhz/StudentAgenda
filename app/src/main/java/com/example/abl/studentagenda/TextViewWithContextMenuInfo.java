package com.example.abl.studentagenda;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

public class TextViewWithContextMenuInfo extends android.support.v7.widget.AppCompatTextView {
    public TextViewWithContextMenuInfo(Context context) {
        super(context);
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return new TextViewContextMenuInfo(this);
    }

    public TextViewWithContextMenuInfo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TextViewWithContextMenuInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static class TextViewContextMenuInfo implements ContextMenu.ContextMenuInfo {
        public TextViewContextMenuInfo(View targetView) {
            this.targetView = (TextView) targetView;
        }

        public TextView targetView;
    }
}
