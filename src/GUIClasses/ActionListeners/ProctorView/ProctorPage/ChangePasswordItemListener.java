package GUIClasses.ActionListeners.ProctorView.ProctorPage;

import GUIClasses.ChangePasswordForm;
import GUIClasses.ProctorViews.ProctorPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordItemListener implements ActionListener {
    private ProctorPage proctorPage;
    public ChangePasswordItemListener(ProctorPage proctorPage){
        this.proctorPage = proctorPage;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new ChangePasswordForm(proctorPage,proctorPage.getProctor().getpId());
    }
}
