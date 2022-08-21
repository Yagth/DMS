package BasicClasses.Requests;

import GUIClasses.StudentViews.RequestForDormitory;

public class RequestForNewDorm extends Request {
    private Address requesterAddress;

    public RequestForNewDorm(String city, String subcity, String woreda,String requesterId){
        super("RequestForNewDorm",requesterId);
        requesterAddress = new Address(city,subcity,woreda);
    }
    public RequestForNewDorm(String city,String requesterId){
        this(city,null,null,requesterId);
    }
    public RequestForNewDorm(String requesterId){
        super("RequestForNewDorm",requesterId);
    }

    public Address getRequesterAddress() {
        return requesterAddress;
    }
    public void setRequesterAddress(String city,String subcity,String woreda){
        this.requesterAddress = new Address(city,subcity,woreda);
    }

    @Override
    public String toString() {
        return requesterAddress.getCity()+"|"+requesterAddress.getSubcity()+"|"+requesterAddress.getWoreda();
    }

    private class Address{
        private String city;
        private String subcity;
        private String woreda;

        public Address(String city, String subcity, String woreda){
            this.city = city;
            this.subcity = subcity;
            this.woreda = woreda;
        }

        public String getCity() {
            return city;
        }

        public String getSubcity() {
            return subcity;
        }

        public String getWoreda() {
            return woreda;
        }
    }

}
