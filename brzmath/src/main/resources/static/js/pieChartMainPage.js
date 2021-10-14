let pieChart = document.getElementById('pieChart').getContext('2d');
let barChart =new Chart(pieChart,{
    type:'pie',
    data:{
        labels:['Google','Facebook','Github'],
        datasets:[{
            label:'Login via social networks',
            data:[
                document.getElementById('google').getAttribute('value'),
                document.getElementById('facebook').getAttribute('value'),
                document.getElementById('github').getAttribute('value')
            ],
            backgroundColor:[
                'rgba(255,99,132,0.6)',
                'rgba(54, 162, 235, 0.6)',
                'rgba(69,146,30,0.6)'
            ],
            borderWidth:1,
            borderColor:'#777',
            hoverBorderWidth:3,
            hoverBorderColor:'#000'
        }]
    }
});