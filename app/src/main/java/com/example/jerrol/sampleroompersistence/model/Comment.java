package com.example.jerrol.sampleroompersistence.model;

import java.util.Date;

/**
 * Created by Jerrol on 11/28/2017.
 */

public interface Comment {
    int getId();
    int getProductId();
    String getText();
    Date getPostedAt();
}
