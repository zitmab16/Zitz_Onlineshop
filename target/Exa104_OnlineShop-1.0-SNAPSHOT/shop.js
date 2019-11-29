/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function increaseAlpacaAmount(articleid) {
    updateCart(articleid, 1);
}
function decreaseAlpacaAmount(articleid) {
    updateCart(articleid, -1);
}
function updateCart(articleid, amount) {
    fetch('./CartUpdateServlet',
            {
                method: 'post',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                   articleid: articleid,
                   amount: amount
                })
            }).then(function (response)
    {
        //if(response.status == 404)

        response.json().then(function (data) {
            console.log(data);
        }
        );
    }
    );
}
