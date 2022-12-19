package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class AddressAdapterBindingImpl extends AddressAdapterBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.edit, 4);
        sViewsWithIds.put(R.id.delete, 5);
    }
    // views
    @NonNull
    private final android.widget.TextView mboundView3;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public AddressAdapterBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private AddressAdapterBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (android.widget.RelativeLayout) bindings[0]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.TextView) bindings[1]
            );
        this.address.setTag(null);
        this.addressItem.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.name.setTag(null);
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
        if (BR.addressadapter == variableId) {
            setAddressadapter((com.utkarshnew.android.address.model.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setAddressadapter(@Nullable com.utkarshnew.android.address.model.Data Addressadapter) {
        this.mAddressadapter = Addressadapter;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.addressadapter);
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
        java.lang.String addressadapterAddressAddressOneJavaLangString = null;
        java.lang.String addressadapterAddressAddressOneJavaLangStringAddressadapterAddressAddressTwo = null;
        java.lang.String javaLangStringPhoneAddressadapterAddressPhone = null;
        com.utkarshnew.android.address.model.Data addressadapter = mAddressadapter;
        java.lang.String addressadapterAddressPhone = null;
        java.lang.String addressadapterAddressAddressOne = null;
        java.lang.String addressadapterAddressAddressTwo = null;
        com.utkarshnew.android.address.model.Address addressadapterAddress = null;
        java.lang.String addressadapterAddressName = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (addressadapter != null) {
                    // read addressadapter.address
                    addressadapterAddress = addressadapter.getAddress();
                }


                if (addressadapterAddress != null) {
                    // read addressadapter.address.phone
                    addressadapterAddressPhone = addressadapterAddress.getPhone();
                    // read addressadapter.address.addressOne
                    addressadapterAddressAddressOne = addressadapterAddress.getAddressOne();
                    // read addressadapter.address.addressTwo
                    addressadapterAddressAddressTwo = addressadapterAddress.getAddressTwo();
                    // read addressadapter.address.name
                    addressadapterAddressName = addressadapterAddress.getName();
                }


                // read ("Phone: ") + (addressadapter.address.phone)
                javaLangStringPhoneAddressadapterAddressPhone = ("Phone: ") + (addressadapterAddressPhone);
                // read (addressadapter.address.addressOne) + (" ")
                addressadapterAddressAddressOneJavaLangString = (addressadapterAddressAddressOne) + (" ");


                // read ((addressadapter.address.addressOne) + (" ")) + (addressadapter.address.addressTwo)
                addressadapterAddressAddressOneJavaLangStringAddressadapterAddressAddressTwo = (addressadapterAddressAddressOneJavaLangString) + (addressadapterAddressAddressTwo);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.address, addressadapterAddressAddressOneJavaLangStringAddressadapterAddressAddressTwo);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, javaLangStringPhoneAddressadapterAddressPhone);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.name, addressadapterAddressName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): addressadapter
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}