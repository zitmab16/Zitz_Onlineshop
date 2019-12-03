/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function showDetails(orderid) {
    fetch('./OrderDetailServlet',
            {
                method: 'post',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    orderid: orderid
                })
            }).then(function (response)
    {
        //if(response.status == 404)

        response.json().then(function (data) {
            console.log(data);

            var table = "<table>";

            table += "<tr> <td>Articlename</td> <td>Amount</td> <td>Price</td></tr>"
            //befüllen der Tabelle
            for (var i = 0; i < data.length; i++)
            {
                table += "<tr>";
                table += "<td>" + data[i]['name'] + "</td>";
                table += "<td>" + data[i]['amount'] + "</td>";
                table += "<td>" + data[i]['price'] + "</td>";
                table += "</tr>";
            }
            table += "</table>";

            console.log(table);
            //den Div Layer Result mit dem Content der var table befüllen
            document.getElementById("result").innerHTML = table;
        }
        );
    }
    );
}


