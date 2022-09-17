package BasicClasses.Enums;

public enum SizeOfMajorClasses {
    WIDTH(1000), HEIGHT(600);
    private int size;
    private SizeOfMajorClasses(int size){
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
