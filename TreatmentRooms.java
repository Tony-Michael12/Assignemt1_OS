
public class TreatmentRooms{
    private Semaphore rooms;
    public TreatmentRooms(int count){
        rooms=new Semaphore(count);
    }
    public boolean reserveARoom(long time){
        return rooms.deadline(time);
    }
    public void leavingRoom(){
        rooms.V();
    }
}