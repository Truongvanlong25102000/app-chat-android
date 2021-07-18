package com.example.messenger.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.messenger.R;
import com.example.messenger.components.adapters.ContactAdapterRV;
import com.example.messenger.models.PhoneContact;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class    PhoneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    ListView listView;
    RecyclerView recyclerView;
    ArrayList<PhoneContact> arrayList = new ArrayList<PhoneContact>();

    public PhoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhoneFragment newInstance(String param1, String param2) {
        PhoneFragment fragment = new PhoneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        //        listView = view.findViewById(R.id.list);
        recyclerView = view.findViewById(R.id.rv_list);
                checkPermission();
//        handleCallPhone();
        return view;
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission( getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else {
            getContactList();
        }
    }

    private void getContactList() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

//        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+"ASC";

        Cursor cursor = getActivity().getApplication().getContentResolver().query(
                uri, null, null,null, null
        );

        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts._ID
                ));

                String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                ));

                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =?";

                Cursor phoneCursor = getActivity().getApplication().getContentResolver().query(
                        uriPhone, null, selection, new String[]{id}, null
                );

                if (phoneCursor.moveToNext()) {
                    String number = phoneCursor.getString(
                            phoneCursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                    );

                    PhoneContact phoneContact = new PhoneContact(name, number);
                    arrayList.add(phoneContact);

                    phoneCursor.close();
                }
            }

            cursor.close();
        }

//        ContactAdapter adapter = new ContactAdapter(getActivity(), R.layout.item_contact, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ContactAdapterRV adapter = new ContactAdapterRV(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getContactList();
        } else {
            Toast.makeText(getActivity(), "Lỗi lấy dữ liệu!", Toast.LENGTH_LONG).show();
            checkPermission();
        }

    }

    public void handleCallPhone() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhoneContact phone = arrayList.get(position);
                Toast.makeText(getActivity(), "Click phone: " + phone.getNumber(), Toast.LENGTH_LONG).show();
            }
        });
    }


}