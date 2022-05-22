
package sample;

public class Patient implements java.io.Serializable {
    private String Name,Mobile,Clinic,DateandTime ;

    public Patient(String Name, String Mobile, String Clinic, String DateandTime) {
        this.Name = Name;
        this.Mobile = Mobile;
        this.Clinic = Clinic;
        this.DateandTime = DateandTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getClinic() {
        return Clinic;
    }

    public void setClinic(String Clinic) {
        this.Clinic = Clinic;
    }

    public String getDateandTime() {
        return DateandTime;
    }

    public void setDateandTime(String DateandTime) {
        this.DateandTime = DateandTime;
    }
}
