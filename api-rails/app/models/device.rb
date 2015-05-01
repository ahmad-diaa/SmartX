class Device < ActiveRecord::Base
	before_save :default_values
  def default_values
    self.favorite ||= "false"
  end
	self.primary_key = 'device_id'
	belongs_to :rooms
	has_one :clicker
	has_many :notes,dependent: :destroy
def to_param
    device_id.parameterize
  end
end
