package BasicClasses.Enums;

public enum SizeOfMajorClasses {
    WIDTH(900), HEIGHT(400);
    private int size;
    private SizeOfMajorClasses(int size){
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
