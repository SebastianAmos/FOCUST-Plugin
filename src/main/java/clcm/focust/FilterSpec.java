package clcm.focust;

public class FilterSpec {
    public double sigma_x;
    public double sigma_y;
    public double sigma_z;

    public double radius_x;
    public double radius_y;
    public double radius_z;

    public double greaterConstant;

    public FilterSpec(double sigma_x, double sigma_y, double sigma_z, double radius_x, double radius_y, double radius_z, double greaterConstant) {
        this.sigma_x = sigma_x;
        this.sigma_y = sigma_y;
        this.sigma_z = sigma_z;
        this.radius_x = radius_x;
        this.radius_y = radius_y;
        this.radius_z = radius_z;
        this.greaterConstant = greaterConstant;
    }
}
