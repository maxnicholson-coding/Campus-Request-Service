public class Technician {
    private String name;
    private int experience;
    public static int numUnknownTechs = 1;
    public Technician(String name, int exp) {
        setName(name);
        setExperience(exp);
    }
    public void setName(String name) { //name cannot match another or be empty/whitespace
        if (name == null || name.trim().isEmpty()) {
            //counts number of unknown names so all names are unique
            boolean invalid = true;
            String unknownName = "";
            while(invalid) { //loops until the next name starting with "Unknown" is availible
                invalid = false;
                unknownName = ("Unknown" + numUnknownTechs);
                for(Technician t : CampusServiceRequest.allTechs) {
                    //only proceeds if the name isn't null nor matches an existing name
                    if (t.getName() != null && t.getName().equals(unknownName)) {
                        invalid = true;
                        break;
                    }
                } //incriment the number of unknown techs
                numUnknownTechs += 1;
            }
            this.name = unknownName;
        } else {
            this.name = name;
        }
    }
    public void setExperience(int e) { //experience must be between 1 and 10 inclusive
        if (e < 1 || e > 10) {
            throw new IllegalArgumentException("Out of range.");
        }
        this.experience = e;
    }
    public String getName() { //returns the tech's name
        return name;
    }
    public int getExperience() { //returns the tech's experience
        return experience;
    }
    public String toString() {
        return (name + " (Experience: " + experience + ")");
    }
}
