package com.pushemover.physics;

import com.pushemover.enums.CollisionSideEnum;

import java.awt.Rectangle;

public class Collision
{
    public static boolean onContact ( Rectangle boundingBox1, Rectangle boundingBox2 )
    {
        return boundingBox1.x < boundingBox2.x + boundingBox2.width &&
               boundingBox1.x + boundingBox1.width > boundingBox2.x &&
               boundingBox1.y < boundingBox2.y + boundingBox2.height &&
               boundingBox1.y + boundingBox1.height > boundingBox2.y;
    }

    public static CollisionSideEnum collision ( Rectangle boundingBoxSource, Rectangle boundingBoxTarget )
    {
        // Dear future programmer reading this,
        //     I do not know how the heck this code works. I am currently trying to understand this as well.
        // I hope you do understand. In the mean, increment the counter below.
        //
        // UNDERSTOOD_THIS = 0;
        // DID_NOT_UNDERSTAND_THIS = 1;
        //
        // Regards,
        // Sean Ballais

        float width = 0.5f * ( boundingBoxSource.width + boundingBoxTarget.width );
        float height = 0.5f * ( boundingBoxSource.height + boundingBoxTarget.height );
        float distanceX = ( boundingBoxSource.width / 2 ) - ( boundingBoxTarget.width / 2 );
        float distanceY = ( boundingBoxSource.height / 2 ) - ( boundingBoxTarget.height / 2 );

        if ( onContact ( boundingBoxSource, boundingBoxTarget ) ) {
            // BA-BANG! COLLISION, MI AMIGO!
            float widthY = width * distanceY;
            float heightX = height * distanceX;

            if ( widthY > heightX ) {
                if ( widthY > -heightX ) {
                    return CollisionSideEnum.LEFT;
                } else {
                    return CollisionSideEnum.TOP;
                }
            } else {
                if ( widthY > -heightX ) {
                    return CollisionSideEnum.RIGHT;
                } else {
                    return CollisionSideEnum.BOTTOM;
                }
            }
        }

        return CollisionSideEnum.NONE;
    }
}
