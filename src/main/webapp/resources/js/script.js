$( function() {
    $( "#datepicker1" ).datepicker( {
        dateFormat: 'yy-mm-dd',
        controlType: 'select',
        changeMonth: true,
        changeYear: true,
        onSelect: function(dateStr)
        {
            $("#datepicker2").datepicker("destroy");
            $("#datepicker2").val(dateStr);
            $("#datepicker2").datepicker({
                minDate: new Date(dateStr),
                dateFormat: 'yy-mm-dd',
                controlType: 'select',
                changeMonth: true,
                changeYear: true,
                firstDay: 1,
                dayNamesMin: [ "Se", "Pi", "An", "Tr", "Ke", "Pe", "Še" ],
                monthNamesShort: [ "Sau", "Vas", "Kov", "Bal", "Geg", "Bir", "Lie", "Rugp", "Rugs", "Spa", "Lap", "Gru" ],
                maxDate: new Date(),
                beforeShowDay: noWeekendsOrHolidays
            })
        },
        firstDay: 1,
        dayNamesMin: [ "Se", "Pi", "An", "Tr", "Ke", "Pe", "Še" ],
        monthNamesShort: [ "Sau", "Vas", "Kov", "Bal", "Geg", "Bir", "Lie", "Rugp", "Rugs", "Spa", "Lap", "Gru" ],
        maxDate: new Date(),
        beforeShowDay: noWeekendsOrHolidays
    });

} );

holiday = [];

function getHolidays() {
    holi = getData();
}
function setHoliDays(date) {
    for (i = 0; i < holi.length; i++) {
        if (date.getFullYear() == holi[i][0]
            && date.getMonth() == holi[i][1] - 1
            && date.getDate() == holi[i][2]) {
            return [false, 'holiday', holi[i][3]];
        }
    }
    return [true, ''];
}

function noWeekendsOrHolidays(date) {
    var noWeekend = $.datepicker.noWeekends(date);
    if (noWeekend[0]) {
        return setHoliDays(date);
    } else {
        return noWeekend;
    }
}

function getData(){
    let holi=[];
    fetch("/holidays")
        .then(res => res.json())
        .then(
            result => {
                holiday = result;
                for(var k in holiday) {
                    if( holiday[k].holidayName =='Easter Sunday' ||
                        holiday[k].holidayName =='Easter Monday' ||
                        holiday[k].holidayName =='New Year\'s Day' ||
                        holiday[k].holidayName =='International Working Day' ||
                        holiday[k].holidayName =='Christmas Day' ||
                        holiday[k].holidayName =='St. Stephen\'s Day') {
                        holi.push([holiday[k].year, holiday[k].month, holiday[k].day, holiday[k].holidayName]);
                    }
                }
            },
            error => {
                console.log(error);
            }
        );
    return holi;
}