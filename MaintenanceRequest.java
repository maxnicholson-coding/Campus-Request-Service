public class MaintenanceRequest extends ServiceRequest {
    public MaintenanceRequest(int id, String title, int priority) {
        super(id, title, priority);
    }
    @Override
    public int estimateMinutes(Technician tech) { //estimates time of completion
        int estimate = (180 - 3 * tech.getExperience() - 2 * getPriority());
        return estimate;
    }
    @Override
    public void assignTo(Technician tech) { //assigns tech to the request
        System.out.println(tech.getName() + " has been assigned to this maintenance request.");
        assignTech(tech);
        System.out.println("Estimated time: " + estimateMinutes(tech) + " minutes.\n");
    }
    @Override
    public String getType() { //returns type of request
        return "Maintenance";
    }
}
