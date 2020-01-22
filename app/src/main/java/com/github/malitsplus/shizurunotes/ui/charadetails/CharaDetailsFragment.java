package com.github.malitsplus.shizurunotes.ui.charadetails;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaBinding;
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaDetailsBinding;
import com.github.malitsplus.shizurunotes.ui.SharedViewModel;

import java.lang.reflect.InvocationTargetException;

public class CharaDetailsFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private CharaDetailsViewModel detailsViewModel;
    private int unitId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this.unitId = CharaDetailsFragmentArgs.fromBundle(getArguments()).getUnitId();
        FragmentCharaDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chara_details, container, false);
        //需要传参数，必须新建一个factory
        CharaDetailsViewModelFactory factory = new CharaDetailsViewModelFactory(unitId);
        detailsViewModel = ViewModelProviders.of(this, factory).get(CharaDetailsViewModel.class);
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        detailsViewModel.setSharedViewModel(sharedViewModel);
        detailsViewModel.inflateData();

        binding.setDetailsViewModel(detailsViewModel);

        //设置观察者
        detailsViewModel.getMutableChara().observe(this, (chara) -> {
            binding.setDetailsViewModel(detailsViewModel);
        });

        return binding.getRoot();
    }

}