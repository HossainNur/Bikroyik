package com.inovex.bikroyik.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.ContactAdapter;
import com.inovex.bikroyik.adapter.Contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by DELL on 8/1/2018.
 */


public class ContactsFragment extends Fragment {
    RecyclerView contactsRecycler;
    List<Contacts> contactList;
    ContactAdapter contactAdapter;
    Context mContext;

    AppDatabaseHelper appDatabaseHelper;

    public ContactsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();

        appDatabaseHelper = new AppDatabaseHelper(getContext());

        //callContactsAPI(mContext);

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        contactsRecycler = (RecyclerView) view.findViewById(R.id.contacts_recycler);

        onBackPressed(view);

        contactList = populateContactsToList();
        contactAdapter = new ContactAdapter(mContext, contactList);
        contactsRecycler.setAdapter(contactAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        contactsRecycler.setLayoutManager(mLayoutManager);
        contactsRecycler.setItemAnimator(new DefaultItemAnimator());
        contactsRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        return view;
    }

    private List<Contacts> populateContactsToList() {

        ArrayList<HashMap<String, String>> contactsList = appDatabaseHelper.getAllContact();
        List<Contacts> contacts = new ArrayList<>();

        for(int i = 0; i < contactsList.size(); i++ ){
            String title = contactsList.get(i).get(AppDatabaseHelper.COLUMN_CONTACTS_TITLE);
            String phone = contactsList.get(i).get(AppDatabaseHelper.COLUMN_CONTACTS_NUMBER);
            String address = contactsList.get(i).get(AppDatabaseHelper.COLUMN_CONTACTS_ADDRESS);
            String type = contactsList.get(i).get(AppDatabaseHelper.COLUMN_CONTACTS_TYPE);
            Contacts contact = new Contacts(title,phone,address,type);
            contacts.add(contact);

        }



        return contacts;
    }

    private void onBackPressed(View view){

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("_back_", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("_back_", "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    SessionManager sessionManager = new SessionManager(getContext());
                    Fragment fragment = null;
                    if (sessionManager.getEmployeeCategory().equals("SR")){
                        fragment = new HomeFragmentSR();

                    }else if (sessionManager.getEmployeeCategory().equalsIgnoreCase("DE")){
                        fragment = new HomeFragmentSR();
                    }
                    Constants.moveHomeFragment(fragment, getActivity(), getContext());
                    return true;
                }
                return false;
            }
        });
    }
}
