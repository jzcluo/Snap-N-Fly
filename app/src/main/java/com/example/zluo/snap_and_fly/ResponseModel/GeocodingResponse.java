package com.example.zluo.snap_and_fly.ResponseModel;

/**
 * Created by zluo on 10/14/17.
 */

public class GeocodingResponse {
    private Result[] results;

    public GeocodingResponse() {

    }

    public Result[] getResults() {
        return this.results;
    }

    public class Result {
        private String formatted_address;
        private Geometry geometry;
        private Address[] address_components;

        public Result() {

        }

        public String getFormatted_address() {
            return this.formatted_address;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public Address[] getAddress_components() {
            return this.address_components;
        }

        public class Address {
            private String long_name;
            private String short_name;
            private String[] types;

            public Address() {

            }

            public String getLong_name() {
                return this.long_name;
            }

            public String getShort_name() {
                return this.short_name;
            }

            public String[] getTypes() {
                return this.types;
            }

        }

        public class Geometry {
            private Location location;

            public Geometry() {

            }
            public Location getLocation() {
                return this.location;
            }

            public class Location {
                private String lat;
                private String lng;

                public Location() {

                }

                public String getLat() {
                    return this.lat;
                }
                public String getLng() {
                    return this.lng;
                }

            }
        }
    }
}
