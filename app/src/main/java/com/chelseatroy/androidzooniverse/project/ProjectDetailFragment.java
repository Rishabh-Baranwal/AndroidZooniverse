package com.chelseatroy.androidzooniverse.project;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chelseatroy.androidzooniverse.R;
import com.chelseatroy.androidzooniverse.provider.ZooniverseContract;

public class ProjectDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_PROJECT_URI = "projectUri";
    private static final int PROJECT_LOADER = 0;

    private Uri mProjectUri;

    public static Fragment newInstance(Uri projectUri) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROJECT_URI, projectUri);

        Fragment fragment = new ProjectDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectUri = getArguments().getParcelable(ARG_PROJECT_URI);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_detail, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        getLoaderManager()
                .initLoader(PROJECT_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                mProjectUri,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        data.moveToFirst();

        View view = getView();
        TextView titleTextView = (TextView) view.findViewById(R.id.title_text);
        titleTextView.setText(data.getString(data.getColumnIndex(ZooniverseContract.Projects.TITLE)));

        TextView textView = (TextView) view.findViewById(R.id.description_text);
        textView.setText(data.getString(data.getColumnIndex(ZooniverseContract.Projects.DESCRIPTION)));

        View goToProjectButton = getView().findViewById(R.id.go_to_project_button);
        goToProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString = data.getString(data.getColumnIndex(ZooniverseContract.Projects.REDIRECT));
                if (uriString == null || uriString.isEmpty()) {
                    uriString = "https://www.zooniverse.org/projects/" + data.getString(data.getColumnIndex(ZooniverseContract.Projects.SLUG));
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // noop
    }
}
