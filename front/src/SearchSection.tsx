import axios from 'axios';
import { useState } from "react";
import './SearchSection.css';
function SearchSection(){//병원 검색 상태
  const [name, setName]=useState('');
  const [address, setAddress]=useState('');
  const [callNumber, setCallNumber]=useState('');
  const [categoryCode, setCategoryCode]=useState('');
  const [results, setResults] = useState([]);

  const handleSearch = async()=>{
    try{
      const response = await axios.get('/api/hospital/search',{
        params:{
          name,
          address,
          callNumber,
          categoryCode,
        },
      });
      setResults(response.data);
    }catch(error){
      console.error('검색 중 오류 발생:',error);
    }
  };

  return(    
    //검색 ui
    <div>
      <div className="search-container">
        <h2>병원 검색</h2>
        <input
          type="text"
          placeholder="병원 이름"
          value={name}
          onChange={(e)=>setName(e.target.value)}
        />
        <input
          type="text"
          placeholder="병원 주소"
          value={address}
          onChange={(e)=>setAddress(e.target.value)}
        />
        <input
          type="text"
          placeholder="전화번호"
          value={callNumber}
          onChange={(e)=>setCallNumber(e.target.value)}
        />
        <input
          type="text"
          placeholder="카테고리 코드"
          value={categoryCode}
          onChange={(e)=>setCategoryCode(e.target.value)}
        />
        <button onClick={handleSearch}>검색</button>
      </div>
      {/*검색 결과*/}
      <div className="search-results">
        {results.map((hospital:any)=>(
          <div key={hospital.hospitalCode} className="result-item">
            <p><strong>{hospital.name}</strong></p>
            <p>{hospital.address}</p>
            <p>{hospital.callNumber}</p>
            <p>카테고리 코드: {hospital.categoryCode}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default SearchSection;