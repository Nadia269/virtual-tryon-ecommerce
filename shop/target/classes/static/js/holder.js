function setToken(newToken) {
    localStorage.setItem('token', newToken);
}

function getToken() {
    return localStorage.getItem('token');
}

function getAuthHeaders() {
    const token = getToken(); 
    return {
        'Authorization': `Bearer ${token}`
    };
}
function setProductId(productId) {
    localStorage.setItem('productId', productId);
}

function getProductId() {
    return localStorage.getItem('productId');
}

function setCategoryName(categoryName){
    localStorage.setItem('categoryName', categoryName);
}

function getCategoryName(){
    return localStorage.getItem('categoryName');
}









function setimg1(img1) {
    localStorage.setItem('img1', img1);
}

function getimg1() {
    return localStorage.getItem('img1');
}






function setimg2(img2) {
    localStorage.setItem('img2', img2);
}

function getimg2() {
    return localStorage.getItem('img2');
}

function setsize(size) {
    localStorage.setItem('size', size);
}

function getsize() {
    return localStorage.getItem('size');
}



function setimg3(img3) {
    localStorage.setItem('img3', img3);
}

function getimg3() {
    return localStorage.getItem('img3');
}

function setimgpro(imgpro) {
    localStorage.setItem('imgpro', imgpro);
}

function getimgpro() {
    return localStorage.getItem('imgpro');
}