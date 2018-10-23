package devnitish.com.simplytext2;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SimplyText extends android.support.v7.widget.AppCompatTextView {

    private static int idCount = 0;
    private static HashMap<Integer,Object> allModification = new HashMap<>();

    // widgets.....
    TextView textView;

    String text = "";
    SpannableString spString;

    int bgStart = -1;
    int bgEnd = -1;
    int bgColor = -1;

    int fgStart = -1;
    int fgEnd = -1;
    int fgColor = -1;

    int strikeStart = -1;
    int strikeEnd = -1;

    int underLineStart = -1;
    int underLineEnd = -1;

    int styleStart = -1;
    int styleEnd = -1;
   int sty;
   Style style;

    int subScriptStart = -1;
    int subScriptEnd = -1;
    int superScriptStart = -1;
    int superScriptEnd = -1;
    int absSizeStart = -1;
    int absSizeEnd = -1;
    int absSize = -1;
    int relSizeStart = -1;
    int relSizeEnd = -1;
    float relSize = -1;

    public enum Style {
        NONE,
        BOLD,
        ITALIC
    }

    public interface SmartClickListener {
        public void onSmartClick();
    }

    public SimplyText(Context context) {
        super(context);

        init();
    }

    public SimplyText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        getAttributes(attrs);
        init();
    }

    public SimplyText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getAttributes(attrs);
        init();
    }

    private void getAttributes(AttributeSet attrs) {

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SimplyText);

        bgStart = array.getInt(R.styleable.SimplyText_bgStart, -1);
        bgEnd = array.getInt(R.styleable.SimplyText_bgEnd, -1);
        bgColor = array.getColor(R.styleable.SimplyText_bgColor, -1);

        fgStart = array.getInt(R.styleable.SimplyText_fgStart, -1);
        fgEnd = array.getInt(R.styleable.SimplyText_fgEnd, -1);
        fgColor = array.getColor(R.styleable.SimplyText_fgColor, -1);

        strikeStart = array.getInt(R.styleable.SimplyText_strikeStart, -1);
        strikeEnd = array.getInt(R.styleable.SimplyText_strikeEnd, -1);

        underLineStart = array.getInt(R.styleable.SimplyText_underLineStart, -1);
        underLineEnd = array.getInt(R.styleable.SimplyText_underLineEnd, -1);

        styleStart = array.getInt(R.styleable.SimplyText_styleStart,-1);
        styleEnd = array.getInt(R.styleable.SimplyText_styleEnd,-1);
        sty = array.getInt(R.styleable.SimplyText_style,0);
        subScriptStart = array.getInt(R.styleable.SimplyText_subScriptStart,-1);
        subScriptEnd = array.getInt(R.styleable.SimplyText_subScriptEnd,-1);
        superScriptStart = array.getInt(R.styleable.SimplyText_superScriptStart,-1);
        superScriptEnd = array.getInt(R.styleable.SimplyText_superScriptEnd,-1);
        absSizeStart = array.getInt(R.styleable.SimplyText_absSizeStart,-1);
        absSizeEnd = array.getInt(R.styleable.SimplyText_absSizeEnd,-1);
        relSizeStart = array.getInt(R.styleable.SimplyText_relSizeStart,-1);
        relSizeEnd = array.getInt(R.styleable.SimplyText_relSizeEnd,-1);
        absSize  = array.getInteger(R.styleable.SimplyText_absSize,0);
        relSize = array.getFloat(R.styleable.SimplyText_relSize,1f);

        if(sty == 0){
            style = Style.NONE;
        }
        else if(sty == 1){
            style = Style.BOLD;
        }
        else if(sty == 2){
            style = Style.ITALIC;
        }

        array.recycle();
    }

    private void init() {

        setClickable(true);
        text = (String) getText();
        spString = new SpannableString(text);


        checkBackgroundSpan();
        applyForegroundSpan();
        applyStrikeSpan();
        applyUnderLine();
        applySuperScript();
        applySubScript();
        applyAbsoluteSize();
        applyRelativeSize();
        applyStyle();

        setText(spString);
    }

    private void checkBackgroundSpan() {

        if (bgStart < 0) {
            return;
        }

        if (bgEnd >= text.length() || bgEnd == -1) {
            bgEnd = text.length();
        }

        BackgroundColorSpan sp = new BackgroundColorSpan((bgColor));
        spString.setSpan(sp, bgStart, bgEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    private void applyForegroundSpan() {

        if (fgStart < 0) {
            return;
        }

        if (fgEnd >= text.length() || fgEnd == -1) {
            fgEnd = text.length();
        }

        ForegroundColorSpan sp = new ForegroundColorSpan((fgColor));
        spString.setSpan(sp, fgStart, fgEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    private void applyStrikeSpan() {


        if (strikeStart < 0) {
            return;
        }

        if (strikeEnd >= text.length() || strikeEnd == -1) {

            strikeEnd = text.length();
        }

        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spString.setSpan(strikethroughSpan, strikeStart, strikeEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


    }

    private void applyUnderLine() {

        if (underLineStart < 0) {
            return;
        }

        if (underLineStart >= text.length() || underLineEnd == -1) {
            underLineEnd = text.length();
        }


        UnderlineSpan underlineSpan = new UnderlineSpan();
        spString.setSpan(underlineSpan, underLineStart, underLineEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void applySuperScript() {

        if (superScriptStart < 0) {
            return;
        }

        if (superScriptEnd >= text.length() || superScriptEnd == -1) {
            superScriptEnd = text.length();
        }


        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        spString.setSpan(superscriptSpan, superScriptStart, superScriptEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    private void applySubScript() {

        if (subScriptStart < 0) {
            return;
        }

        if (subScriptEnd >= text.length() || subScriptEnd == -1) {
            subScriptEnd = text.length();
        }


        SubscriptSpan subscriptSpan = new SubscriptSpan();
        spString.setSpan(subscriptSpan, subScriptStart, subScriptEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void applyAbsoluteSize() {

        if (absSizeStart < 0) {
            return;
        }

        if (absSizeEnd >= text.length() || absSizeEnd == -1) {
            absSizeEnd = text.length();
        }


        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(absSize,true);
        spString.setSpan(absoluteSizeSpan, absSizeStart, absSizeEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

    private void applyRelativeSize() {

        if (relSizeStart < 0) {
            return;
        }

        if (relSizeEnd >= text.length() || relSizeEnd == -1) {
            relSizeEnd = text.length();
        }


        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(relSize);
        spString.setSpan(relativeSizeSpan, relSizeStart, relSizeEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    private void applyStyle() {

        if (styleStart < 0) {
            return;
        }

        if (styleEnd >= text.length() || styleEnd == -1) {
            styleEnd = text.length();
        }

        int s= Typeface.NORMAL;

        if(style == Style.BOLD){
            s = Typeface.BOLD;
        }
        else if( style == Style.ITALIC){
            s = Typeface.ITALIC;
        }

        StyleSpan styleSpan = new StyleSpan(s);
        spString.setSpan(styleSpan, styleStart, styleEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    public void changeText(String s){

        text = s;
        spString = new SpannableString(s);
        setText(spString);
        allModification.clear();

    }

    public void updateState(){

        text = (String) getText();
        spString = new SpannableString(text);
        allModification.clear();

    }

    public int setBackground(int start,int end,int colorResourceId){

        if (start < 0) {
            return -1;
        }

        if (end >= text.length() || end == -1) {
            end = text.length();
        }

        BackgroundColorSpan sp = new BackgroundColorSpan(getContext().getResources().getColor(colorResourceId));
        spString.setSpan(sp, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ++idCount;
        allModification.put(idCount,sp);
        setText(spString);

        return idCount;
    }

    public int setForeground(int start,int end,int colorResourceId) {

        if (start < 0) {
            return -1;
        }

        if (end >= text.length() || end == -1) {
            end = text.length();
        }

        ForegroundColorSpan sp = new ForegroundColorSpan(getContext().getResources().getColor(colorResourceId));
        spString.setSpan(sp, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ++idCount;
        allModification.put(idCount,sp);
        setText(spString);
        return idCount;
    }


    public int setStrike(int start,int end) {


        if (start < 0) {
            return -1;
        }

        if (end >= text.length() || end == -1) {

            end = text.length();
        }

        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spString.setSpan(strikethroughSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ++idCount;
        allModification.put(idCount,strikethroughSpan);
        setText(spString);
        return idCount;

    }

    public int setUnderLine(int start,int end) {

        if (start < 0) {
            return -1;
        }

        if (start >= text.length() || start == -1) {
            start = text.length();
        }


        UnderlineSpan underlineSpan = new UnderlineSpan();
        spString.setSpan(underlineSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ++idCount;
        allModification.put(idCount,underlineSpan);
        setText(spString);
        return idCount;
    }

    public int setSuperScript(int start,int end) {

        if (start < 0) {
            return -1;
        }

        if (end >= text.length() || end == -1) {
            end = text.length();
        }


        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        spString.setSpan(superscriptSpan, start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ++idCount;
        allModification.put(idCount,superscriptSpan);
        setText(spString);
        return idCount;
    }


    public int setSubScript(int start,int end) {

        if (start < 0) {
            return -1;
        }

        if (end >= text.length() || end == -1) {
            end = text.length();
        }

        SubscriptSpan subscriptSpan = new SubscriptSpan();
        spString.setSpan(subscriptSpan, start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ++idCount;
        allModification.put(idCount,subscriptSpan);
        setText(spString);
        return idCount;
    }

    public int setAbsoluteSize(int start,int end,int size) {

        if (start < 0) {
            return -1;
        }

        if (end >= text.length() || end == -1) {
            end = text.length();
        }


        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(size,true);
        spString.setSpan(absoluteSizeSpan, start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ++idCount;
        allModification.put(idCount,absoluteSizeSpan);
        setText(spString);
        return idCount;

    }

    public int setRelativeSize(int start,int end,float relSize) {

        if (start < 0) {
            return -1;
        }

        if (end >= text.length() || end == -1) {
            end = text.length();
        }


        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(relSize);
        spString.setSpan(relativeSizeSpan, start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ++idCount;
        allModification.put(idCount,relativeSizeSpan);
        setText(spString);
        return idCount;
    }

    public int setStyle(int start,int end,Style style) {

        if (start < 0) {
            return -1;
        }

        if (end >= text.length() || end == -1) {
            end = text.length();
        }

        int s= Typeface.NORMAL;

        if(style == Style.BOLD){
            s = Typeface.BOLD;
        }
        else if( style == Style.ITALIC){
            s = Typeface.ITALIC;
        }

        StyleSpan styleSpan = new StyleSpan(s);
        spString.setSpan(styleSpan, start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ++idCount;
        allModification.put(idCount,styleSpan);
        setText(spString);
        return idCount;

    }

    public int setSmartClickable(int start, int end,
                                 final SmartClickListener smartClickListener){

        if(start<0 || start>text.length()){
            return -1;
        }

        if (end >= text.length() || end == -1) {
            end = text.length();
        }

        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

                if(smartClickListener!=null){
                    smartClickListener.onSmartClick();
                }
            }
        };

        spString.setSpan(span,start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spString);
        setMovementMethod(LinkMovementMethod.getInstance());

        ++idCount;
        allModification.put(idCount,span);
        return idCount;
    }


    public boolean removeFormating(int formatingId){

        Object o = allModification.get(formatingId);

        if(o == null){
            return false;
        }

        spString.removeSpan(o);
        allModification.remove(formatingId);

        setText(spString);
        return true;
    }

    public void removeAllFormating(){

        ArrayList<Integer> o = new ArrayList<Integer>(allModification.keySet());

        for(Integer o1 : o){

            spString.removeSpan(allModification.get(o1));

        }

        allModification.clear();

        setText(spString);

    }
}
