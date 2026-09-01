package application;

//Gene--> [Course+Slot]
public class Gene {
    private String Course_Code;
    private String Slot_ID;
//Course+Slot-->newGene
    public Gene(String Course_Code, String Slot_ID) {
        this.Course_Code = Course_Code;
        this.Slot_ID = Slot_ID;
    }

    public String getCourse_Code() {
        return Course_Code;
    }

    public String getSlot_ID() {
        return Slot_ID;
    }

    public void setSlot_ID(String Slot_ID) {
        this.Slot_ID = Slot_ID;
    }
}