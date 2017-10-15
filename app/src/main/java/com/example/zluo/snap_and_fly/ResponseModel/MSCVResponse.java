package com.example.zluo.snap_and_fly.ResponseModel;

/**
 * Created by zluo on 10/14/17.
 */

public class MSCVResponse {
    private LandMarkResult result;

    public MSCVResponse() {

    }

    public LandMarkResult getResult() {
        return this.result;
    }


    public class LandMarkResult {
        private LandMark[] landmarks;

        public LandMarkResult() {

        }

        public LandMark[] getLandmarks() {
            return this.landmarks;
        }

        public class LandMark {
            private String name;

            public LandMark() {

            }

            public String getName() {
                return this.name;
            }
        }

    }


}
