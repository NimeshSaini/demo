package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityPurchaseHistoryDetailBindingSw720dpImpl extends ActivityPurchaseHistoryDetailBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.main_toolbar, 9);
        sViewsWithIds.put(R.id.image_back, 10);
        sViewsWithIds.put(R.id.toolbarTitleTV, 11);
        sViewsWithIds.put(R.id.select_all_delete, 12);
        sViewsWithIds.put(R.id.delete, 13);
        sViewsWithIds.put(R.id.layout1, 14);
        sViewsWithIds.put(R.id.layout2, 15);
        sViewsWithIds.put(R.id.payment_id, 16);
        sViewsWithIds.put(R.id.layout3, 17);
        sViewsWithIds.put(R.id.payment_status, 18);
        sViewsWithIds.put(R.id.layout4, 19);
        sViewsWithIds.put(R.id.payment_mode, 20);
        sViewsWithIds.put(R.id.layout5, 21);
        sViewsWithIds.put(R.id.payemnt_date, 22);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView3;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.TextView mboundView5;
    @NonNull
    private final android.widget.TextView mboundView6;
    @NonNull
    private final android.widget.TextView mboundView7;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPurchaseHistoryDetailBindingSw720dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }
    private ActivityPurchaseHistoryDetailBindingSw720dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[1]
            , (android.widget.TextView) bindings[13]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[17]
            , (android.widget.RelativeLayout) bindings[19]
            , (android.widget.RelativeLayout) bindings[21]
            , (androidx.appcompat.widget.Toolbar) bindings[9]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[18]
            , (android.widget.CheckBox) bindings[12]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[8]
            );
        this.courseImagebg.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView7 = (android.widget.TextView) bindings[7];
        this.mboundView7.setTag(null);
        this.trackOrder.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.pruchasehistory == variableId) {
            setPruchasehistory((com.utkarshnew.android.purchasehistory.model.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPruchasehistory(@Nullable com.utkarshnew.android.purchasehistory.model.Data Pruchasehistory) {
        this.mPruchasehistory = Pruchasehistory;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.pruchasehistory);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String pruchasehistoryPurchaseDate = null;
        java.lang.String pruchasehistoryPayViaEmptyBooleanTruePruchasehistoryPayViaJavaLangObjectNullJavaLangStringNAPruchasehistoryPayVia = null;
        java.lang.String pruchasehistoryPaymentIdEmptyBooleanTruePruchasehistoryPaymentIdJavaLangObjectNullJavaLangStringNAPruchasehistoryPaymentId = null;
        java.lang.String javaLangStringPruchasehistoryMrp = null;
        boolean pruchasehistoryPaymentIdEmptyBooleanTruePruchasehistoryPaymentIdJavaLangObjectNull = false;
        boolean pruchasehistoryPayViaJavaLangObjectNull = false;
        boolean pruchasehistoryTrackUrl1EmptyBooleanTruePruchasehistoryTrackUrl1JavaLangObjectNull = false;
        boolean pruchasehistoryTrackUrl1JavaLangObjectNull = false;
        int pruchasehistoryTrackUrl1EmptyBooleanTruePruchasehistoryTrackUrl1JavaLangObjectNullViewGONEViewVISIBLE = 0;
        java.lang.String pruchasehistoryTitle = null;
        java.lang.String pruchasehistoryMrp = null;
        boolean pruchasehistoryPayViaEmptyBooleanTruePruchasehistoryPayViaJavaLangObjectNull = false;
        boolean pruchasehistoryPaymentIdEmpty = false;
        boolean pruchasehistoryPaymentIdJavaLangObjectNull = false;
        java.lang.String pruchasehistoryMrpEqualsIgnoreCaseJavaLangString0JavaLangStringFreeJavaLangStringPruchasehistoryMrp = null;
        java.lang.String pruchasehistoryTransactionStatus = null;
        boolean pruchasehistoryPayViaEmpty = false;
        java.lang.String pruchasehistoryCoverImage = null;
        java.lang.String pruchasehistoryPaymentId = null;
        java.lang.String pruchasehistoryPayVia = null;
        boolean pruchasehistoryTrackUrl1Empty = false;
        com.utkarshnew.android.purchasehistory.model.Data pruchasehistory = mPruchasehistory;
        boolean pruchasehistoryMrpEqualsIgnoreCaseJavaLangString0 = false;
        java.lang.String pruchasehistoryTrackUrl1 = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (pruchasehistory != null) {
                    // read pruchasehistory.purchase_date
                    pruchasehistoryPurchaseDate = pruchasehistory.getPurchase_date();
                    // read pruchasehistory.title
                    pruchasehistoryTitle = pruchasehistory.getTitle();
                    // read pruchasehistory.mrp
                    pruchasehistoryMrp = pruchasehistory.getMrp();
                    // read pruchasehistory.transaction_status
                    pruchasehistoryTransactionStatus = pruchasehistory.getTransaction_status();
                    // read pruchasehistory.cover_image
                    pruchasehistoryCoverImage = pruchasehistory.getCover_image();
                    // read pruchasehistory.payment_id
                    pruchasehistoryPaymentId = pruchasehistory.getPayment_id();
                    // read pruchasehistory.pay_via
                    pruchasehistoryPayVia = pruchasehistory.getPay_via();
                    // read pruchasehistory.track_url_1
                    pruchasehistoryTrackUrl1 = pruchasehistory.getTrack_url_1();
                }


                if (pruchasehistoryMrp != null) {
                    // read pruchasehistory.mrp.equalsIgnoreCase("0")
                    pruchasehistoryMrpEqualsIgnoreCaseJavaLangString0 = pruchasehistoryMrp.equalsIgnoreCase("0");
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(pruchasehistoryMrpEqualsIgnoreCaseJavaLangString0) {
                        dirtyFlags |= 0x8000L;
                }
                else {
                        dirtyFlags |= 0x4000L;
                }
            }
                if (pruchasehistoryPaymentId != null) {
                    // read pruchasehistory.payment_id.empty
                    pruchasehistoryPaymentIdEmpty = pruchasehistoryPaymentId.isEmpty();
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(pruchasehistoryPaymentIdEmpty) {
                        dirtyFlags |= 0x80L;
                }
                else {
                        dirtyFlags |= 0x40L;
                }
            }
                if (pruchasehistoryPayVia != null) {
                    // read pruchasehistory.pay_via.empty
                    pruchasehistoryPayViaEmpty = pruchasehistoryPayVia.isEmpty();
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(pruchasehistoryPayViaEmpty) {
                        dirtyFlags |= 0x2000L;
                }
                else {
                        dirtyFlags |= 0x1000L;
                }
            }
                if (pruchasehistoryTrackUrl1 != null) {
                    // read pruchasehistory.track_url_1.empty
                    pruchasehistoryTrackUrl1Empty = pruchasehistoryTrackUrl1.isEmpty();
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(pruchasehistoryTrackUrl1Empty) {
                        dirtyFlags |= 0x200L;
                }
                else {
                        dirtyFlags |= 0x100L;
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x4000L) != 0) {

                // read ("₹ ") + (pruchasehistory.mrp)
                javaLangStringPruchasehistoryMrp = ("₹ ") + (pruchasehistoryMrp);
        }
        if ((dirtyFlags & 0x1000L) != 0) {

                // read pruchasehistory.pay_via == null
                pruchasehistoryPayViaJavaLangObjectNull = (pruchasehistoryPayVia) == (null);
        }
        if ((dirtyFlags & 0x100L) != 0) {

                // read pruchasehistory.track_url_1 == null
                pruchasehistoryTrackUrl1JavaLangObjectNull = (pruchasehistoryTrackUrl1) == (null);
        }
        if ((dirtyFlags & 0x40L) != 0) {

                // read pruchasehistory.payment_id == null
                pruchasehistoryPaymentIdJavaLangObjectNull = (pruchasehistoryPaymentId) == (null);
        }

        if ((dirtyFlags & 0x3L) != 0) {

                // read pruchasehistory.payment_id.empty ? true : pruchasehistory.payment_id == null
                pruchasehistoryPaymentIdEmptyBooleanTruePruchasehistoryPaymentIdJavaLangObjectNull = ((pruchasehistoryPaymentIdEmpty) ? (true) : (pruchasehistoryPaymentIdJavaLangObjectNull));
                // read pruchasehistory.track_url_1.empty ? true : pruchasehistory.track_url_1 == null
                pruchasehistoryTrackUrl1EmptyBooleanTruePruchasehistoryTrackUrl1JavaLangObjectNull = ((pruchasehistoryTrackUrl1Empty) ? (true) : (pruchasehistoryTrackUrl1JavaLangObjectNull));
                // read pruchasehistory.pay_via.empty ? true : pruchasehistory.pay_via == null
                pruchasehistoryPayViaEmptyBooleanTruePruchasehistoryPayViaJavaLangObjectNull = ((pruchasehistoryPayViaEmpty) ? (true) : (pruchasehistoryPayViaJavaLangObjectNull));
                // read pruchasehistory.mrp.equalsIgnoreCase("0") ? "Free" : ("₹ ") + (pruchasehistory.mrp)
                pruchasehistoryMrpEqualsIgnoreCaseJavaLangString0JavaLangStringFreeJavaLangStringPruchasehistoryMrp = ((pruchasehistoryMrpEqualsIgnoreCaseJavaLangString0) ? ("Free") : (javaLangStringPruchasehistoryMrp));
            if((dirtyFlags & 0x3L) != 0) {
                if(pruchasehistoryPaymentIdEmptyBooleanTruePruchasehistoryPaymentIdJavaLangObjectNull) {
                        dirtyFlags |= 0x20L;
                }
                else {
                        dirtyFlags |= 0x10L;
                }
            }
            if((dirtyFlags & 0x3L) != 0) {
                if(pruchasehistoryTrackUrl1EmptyBooleanTruePruchasehistoryTrackUrl1JavaLangObjectNull) {
                        dirtyFlags |= 0x800L;
                }
                else {
                        dirtyFlags |= 0x400L;
                }
            }
            if((dirtyFlags & 0x3L) != 0) {
                if(pruchasehistoryPayViaEmptyBooleanTruePruchasehistoryPayViaJavaLangObjectNull) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }


                // read pruchasehistory.track_url_1.empty ? true : pruchasehistory.track_url_1 == null ? View.GONE : View.VISIBLE
                pruchasehistoryTrackUrl1EmptyBooleanTruePruchasehistoryTrackUrl1JavaLangObjectNullViewGONEViewVISIBLE = ((pruchasehistoryTrackUrl1EmptyBooleanTruePruchasehistoryTrackUrl1JavaLangObjectNull) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
        }
        // batch finished

        if ((dirtyFlags & 0x3L) != 0) {

                // read pruchasehistory.pay_via.empty ? true : pruchasehistory.pay_via == null ? "NA" : pruchasehistory.pay_via
                pruchasehistoryPayViaEmptyBooleanTruePruchasehistoryPayViaJavaLangObjectNullJavaLangStringNAPruchasehistoryPayVia = ((pruchasehistoryPayViaEmptyBooleanTruePruchasehistoryPayViaJavaLangObjectNull) ? ("NA") : (pruchasehistoryPayVia));
                // read pruchasehistory.payment_id.empty ? true : pruchasehistory.payment_id == null ? "NA" : pruchasehistory.payment_id
                pruchasehistoryPaymentIdEmptyBooleanTruePruchasehistoryPaymentIdJavaLangObjectNullJavaLangStringNAPruchasehistoryPaymentId = ((pruchasehistoryPaymentIdEmptyBooleanTruePruchasehistoryPaymentIdJavaLangObjectNull) ? ("NA") : (pruchasehistoryPaymentId));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.ExtensionFucationKt.loadImage(this.courseImagebg, pruchasehistoryCoverImage);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, pruchasehistoryTitle);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, pruchasehistoryMrpEqualsIgnoreCaseJavaLangString0JavaLangStringFreeJavaLangStringPruchasehistoryMrp);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, pruchasehistoryPaymentIdEmptyBooleanTruePruchasehistoryPaymentIdJavaLangObjectNullJavaLangStringNAPruchasehistoryPaymentId);
            com.utkarshnew.android.feeds.ExtensionFucationKt.transactionstatus(this.mboundView5, pruchasehistoryTransactionStatus);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView6, pruchasehistoryPayViaEmptyBooleanTruePruchasehistoryPayViaJavaLangObjectNullJavaLangStringNAPruchasehistoryPayVia);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.mboundView7, pruchasehistoryPurchaseDate);
            this.trackOrder.setVisibility(pruchasehistoryTrackUrl1EmptyBooleanTruePruchasehistoryTrackUrl1JavaLangObjectNullViewGONEViewVISIBLE);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): pruchasehistory
        flag 1 (0x2L): null
        flag 2 (0x3L): pruchasehistory.pay_via.empty ? true : pruchasehistory.pay_via == null ? "NA" : pruchasehistory.pay_via
        flag 3 (0x4L): pruchasehistory.pay_via.empty ? true : pruchasehistory.pay_via == null ? "NA" : pruchasehistory.pay_via
        flag 4 (0x5L): pruchasehistory.payment_id.empty ? true : pruchasehistory.payment_id == null ? "NA" : pruchasehistory.payment_id
        flag 5 (0x6L): pruchasehistory.payment_id.empty ? true : pruchasehistory.payment_id == null ? "NA" : pruchasehistory.payment_id
        flag 6 (0x7L): pruchasehistory.payment_id.empty ? true : pruchasehistory.payment_id == null
        flag 7 (0x8L): pruchasehistory.payment_id.empty ? true : pruchasehistory.payment_id == null
        flag 8 (0x9L): pruchasehistory.track_url_1.empty ? true : pruchasehistory.track_url_1 == null
        flag 9 (0xaL): pruchasehistory.track_url_1.empty ? true : pruchasehistory.track_url_1 == null
        flag 10 (0xbL): pruchasehistory.track_url_1.empty ? true : pruchasehistory.track_url_1 == null ? View.GONE : View.VISIBLE
        flag 11 (0xcL): pruchasehistory.track_url_1.empty ? true : pruchasehistory.track_url_1 == null ? View.GONE : View.VISIBLE
        flag 12 (0xdL): pruchasehistory.pay_via.empty ? true : pruchasehistory.pay_via == null
        flag 13 (0xeL): pruchasehistory.pay_via.empty ? true : pruchasehistory.pay_via == null
        flag 14 (0xfL): pruchasehistory.mrp.equalsIgnoreCase("0") ? "Free" : ("₹ ") + (pruchasehistory.mrp)
        flag 15 (0x10L): pruchasehistory.mrp.equalsIgnoreCase("0") ? "Free" : ("₹ ") + (pruchasehistory.mrp)
    flag mapping end*/
    //end
}