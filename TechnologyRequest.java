public class TechnologyRequest extends ServiceRequest {
    public TechnologyRequest(int id, String title, int priority) {
        super(id, title, priority);
    }
    @Override
    public int estimateMinutes(Technician tech) { //estimates time of completion
        int estimate = (270 - 5 * tech.getExperience() - 3 * getPriority());
        return estimate;
    }
    @Override
    public void assignTo(Technician tech) { //assigns tech to the request
        System.out.println(tech.getName() + " has been assigned to this technology request.");
        assignTech(tech);
        System.out.println("Estimated time: " + estimateMinutes(tech) + " minutes.");
    }
    @Override
    public String getType() { //returns type of request
        return "Technology";
    }
}
