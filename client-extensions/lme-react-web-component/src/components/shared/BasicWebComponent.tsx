import React from 'react';

interface Props  { 
    children: React.ReactNode
 }

function BasicWebComponent({ children }: Props) {
    return (
        <>
             <style type="text/css">
                @import "/o/mtui-css/main.bundle.css";
                @import "/o/classic-theme/css/clay.css?browserId=chrome&themeId=classic_WAR_classictheme&minifierType=css&languageId=pt_BR&t=1717402586000";
                @import "/o/mtui-webfonts/all.min.css";
                @import "https://fonts.googleapis.com/css2?family=Nunito+Sans:ital,opsz,wdth,wght@0,6..12,87.5,200..1000;1,6..12,87.5,200..1000&display=swap";
            </style>

            <div className='webcomponent'>
                {children}
            </div>
        </>

    )
}
export default BasicWebComponent