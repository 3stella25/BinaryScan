package cs1302.api;

/**
 * Represents security information returned in json response from IPWHOIS api.
 */
public class Security {
    boolean anonymous = false;
    boolean proxy = false;
    boolean vpn = false;
    boolean tor = false;
    boolean hosting = false;
}
