import React from 'react';

import BasicWebComponent from '@components/shared/BasicWebComponent';

interface Props {
    icon: String
    title: String
    liferayProps: String

}
function CardIcon({ icon, title, liferayProps }: Props) {

    const prefix = "mtui"

    function hasValue(str: String | null | undefined): boolean {
        return str !== null && str !== undefined && str.trim() !== "";
    }

    return (

        <BasicWebComponent>

            <div
                className={`${prefix}-card`}
            >

                <div className={`${prefix}-card__wrapper d-flex flex-column justify-content-center align-items-center h-100`}>

                    {hasValue(icon) &&
                        <div className={`${prefix}-card__icon rounded-circle bg-primary d-flex justify-content-center align-items-center`}  style={{width: '75px', height: '75px'}}>
                            <i className={`fa ${icon}`} style={{color: '#fff'}}></i>
                        </div>
                    }

                    <div className={`${prefix}-card__title h4 line-clamp-3 h-3l py-2 text-center`}>
                        {title}
                    </div>


                </div>

            </div>

        </BasicWebComponent>

    )
}

export default CardIcon